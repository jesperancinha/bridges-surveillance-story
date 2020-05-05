package org.jesperancinha.logistics.readings

import java.util.UUID

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Durations, StreamingContext}
import org.jesperancinha.logistics.passengers.readings.Passenger
import play.api.libs.json.Json

object PassengersReadingsLauncher extends App {

  override def main(args: Array[String]): Unit = {
    val DEMO: String = "demo"
    val LOCAL: String = "local"

    val env: String = args.length match {
      case 0 => LOCAL
      case _ => args(0) match {
        case DEMO => DEMO
        case _ =>
          LOCAL
      }
    }

    val config = ConfigFactory.load("application.conf").getConfig("org.jesperancinha.logistics")
    val envConfig = config.getConfig(env)
    val sparkConfig = config.getConfig("spark")
    val appName = sparkConfig.getString("app-name")
    val kafkaHost1 = envConfig.getString("kafka-host-1")
    val kafkaHost2 = envConfig.getString("kafka-host-2")
    val cassandraHost = envConfig.getString("cassandra-host")
    println(appName)

    val sparkConf = new SparkConf
    sparkConf.setAppName("BridgeLogisticsReader")
    sparkConf.setMaster("local[*]")
    sparkConf.set("spark.cassandra.connection.host", cassandraHost)
    sparkConf.set("spark.local.dir", "/tmp/spark-temp");
    val streamingContext = new StreamingContext(sparkConf, Durations.seconds(10))
    val sc = streamingContext.sparkContext

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> (kafkaHost1 + ":9098," + kafkaHost2 + ":9099"),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "0",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val passengerTopics: Array[String] = Array("PASSENGER")
    val passengerStream: org.apache.spark.streaming.dstream.InputDStream[ConsumerRecord[String, String]]
    = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](passengerTopics, kafkaParams))

    val connector = CassandraConnector.apply(sc)

    try {
      val session = connector.openSession()
      try {
        //        session.execute("DROP KEYSPACE IF EXISTS readings")
        session.execute("CREATE KEYSPACE readings WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}")
        session.execute("CREATE TABLE readings.passengers (" +
          "id UUID PRIMARY KEY, " +
          "passenger_id BIGINT, " +
          "first_name TEXT, " +
          "last_name TEXT, " +
          "gender TEXT, " +
          "carriage_id BIGINT, " +
          "weight BIGINT, " +
          "lat DECIMAL, " +
          "lon DECIMAL, " +
          "time_of_reading BIGINT)")
      } finally if (session != null) session.close()
    }

    val rdd = sc.cassandraTable("readings", "passengers")
    val file_collect = rdd.collect().take(100)
    file_collect.foreach(println(_))

    passengerStream.foreachRDD { rdd =>
      System.out.println("--- New RDD with " + rdd.partitions.length + " partitions and " + rdd.count + " records")
      val strings = rdd.map(record => record.value()).collect();
      strings.foreach(temperatureString => {
        try {
          System.out.println("--- New RDD id " + rdd.id)
          val passenger = Json.fromJson[Passenger](Json.parse(temperatureString)).get
          val collection = sc.parallelize(Seq((
            UUID.randomUUID(),
            passenger.id,
            passenger.firstName,
            passenger.lastName,
            passenger.gender,
            passenger.carriageId,
            passenger.weight,
            passenger.status,
            passenger.lat,
            passenger.lon,
            passenger.timeOfReading
          )))
          collection.saveToCassandra("readings", "passengers",
            SomeColumns("id",
              "passenger_id", "first_name", "last_name", "gender",
              "carriage_id",
              "weight",
              "status",
              "lat",
              "lon",
              "time_of_reading"))
        } catch {
          case e: java.util.NoSuchElementException => println("This data doesn't make sense" + e.getMessage)
        }
      })
    }

    println("***************************************************************************************************************")
    println(passengerStream.id)
    println(passengerStream.count())
    println("***************************************************************************************************************")

    streamingContext.start
    streamingContext.awaitTermination()
  }
}