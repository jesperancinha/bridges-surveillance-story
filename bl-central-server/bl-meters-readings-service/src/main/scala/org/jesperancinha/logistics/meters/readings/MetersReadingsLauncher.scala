package org.jesperancinha.logistics.meters.readings

import java.util.UUID

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Durations, StreamingContext}
import play.api.libs.json.Json

object MetersReadingsLauncher extends App {

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
    val kafkaHost3 = envConfig.getString("kafka-host-3")
    val cassandraHost = envConfig.getString("cassandra-host")
    println(appName)

    val sparkConf = new SparkConf
    sparkConf.setAppName("MetersBridgeLogisticsReader")
    sparkConf.setMaster("local[*]")
    sparkConf.set("spark.cassandra.connection.host", cassandraHost)
    sparkConf.set("spark.local.dir", "/tmp/spark-temp");
    val streamingContext = new StreamingContext(sparkConf, Durations.seconds(10))
    val sc = streamingContext.sparkContext

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> (kafkaHost1 + ":9092," + kafkaHost2 + ":9093," + kafkaHost3 + ":9094"),
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "0",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val temperatureTopics: Array[String] = Array("TEMPERATURE")
    val temperatureStream: org.apache.spark.streaming.dstream.InputDStream[ConsumerRecord[String, String]]
    = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](temperatureTopics, kafkaParams))

    val humidityTopics: Array[String] = Array("HUMIDITY")
    val humidityStream: org.apache.spark.streaming.dstream.InputDStream[ConsumerRecord[String, String]]
    = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](humidityTopics, kafkaParams))

    val connector = CassandraConnector.apply(sc)

    try {
      val session = connector.openSession()
      try {
//        session.execute("DROP KEYSPACE IF EXISTS readings")
        session.execute("CREATE KEYSPACE IF NOT EXISTS readings WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}")
        session.execute("CREATE TABLE IF NOT EXISTS readings.temperatures (" +
          "id UUID PRIMARY KEY, " +
          "device_id INT, " +
          "device_serial_number TEXT, " +
          "device_type TEXT, " +
          "unit TEXT, " +
          "time_of_reading BIGINT, " +
          "reading BIGINT)")
        session.execute("CREATE TABLE IF NOT EXISTS readings.humidity (" +
          "id UUID PRIMARY KEY, " +
          "device_id INT, " +
          "device_serial_number TEXT, " +
          "device_type TEXT, " +
          "unit TEXT, " +
          "time_of_reading BIGINT, " +
          "reading BIGINT)")
      } finally if (session != null) session.close()
    }

    val rdd = sc.cassandraTable("readings", "temperatures")
    val file_collect = rdd.collect().take(100)
    file_collect.foreach(println(_))

    temperatureStream.foreachRDD { rdd =>
      System.out.println("--- New RDD with " + rdd.partitions.length + " partitions and " + rdd.count + " records")
      val strings = rdd.map(record => record.value()).collect();
      strings.foreach(temperatureString => {
        try {
          System.out.println("--- New RDD id " + rdd.id)
          val temperature = Json.fromJson[Temperature](Json.parse(temperatureString)).get
          val collection = sc.parallelize(Seq((
            UUID.randomUUID(),
            temperature.deviceId,
            temperature.deviceSerialNumber,
            temperature.deviceType,
            temperature.unit,
            temperature.timeOfReading,
            temperature.reading
          )))
          collection.saveToCassandra("readings", "temperatures",
            SomeColumns("id", "device_id", "device_serial_number", "device_type", "unit", "time_of_reading", "reading"))
        } catch {
          case e: java.util.NoSuchElementException => println("This data doesn't make sense"+ e.getMessage)
        }
      })
    }

    val rddH = sc.cassandraTable("readings", "humidity")
    val file_collectH = rddH.collect().take(100)
    file_collectH.foreach(println(_))

    humidityStream.foreachRDD { rdd =>
      System.out.println("--- New RDD with " + rdd.partitions.length + " partitions and " + rdd.count + " records")
      val strings = rdd.map(record => record.value()).collect();
      strings.foreach(humidityString => {
        try {
          System.out.println("--- New RDD id " + rdd.id)
          val humidity = Json.fromJson[Humidity](Json.parse(humidityString)).get
          val collection = sc.parallelize(Seq((
            UUID.randomUUID(),
            humidity.deviceId,
            humidity.deviceSerialNumber,
            humidity.deviceType,
            humidity.unit,
            humidity.timeOfReading,
            humidity.reading
          )))
          collection.saveToCassandra("readings", "humidity",
            SomeColumns("id", "device_id", "device_serial_number", "device_type", "unit", "time_of_reading", "reading"))
        } catch {
          case e: java.util.NoSuchElementException => println("This data doesn't make sense"+ e.getMessage)
        }
      })
    }

    println("***************************************************************************************************************")
    println(temperatureStream.id)
    println(temperatureStream.count())
    println("***************************************************************************************************************")

    println("***************************************************************************************************************")
    println(humidityStream.id)
    println(humidityStream.count())
    println("***************************************************************************************************************")

    streamingContext.start
    streamingContext.awaitTermination()
  }
}