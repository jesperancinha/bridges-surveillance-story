package org.jesperancinha.bridgelogistics.readings

import java.util.UUID

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Durations, StreamingContext}
import play.api.libs.json.Json

object ReadingsLauncher extends App {

  override def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf
    sparkConf.setAppName("WordCountingApp")
    sparkConf.setMaster("local[*]")

    val streamingContext = new StreamingContext(sparkConf, Durations.seconds(10))
    val sc = streamingContext.sparkContext

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9090,localhost:9091,localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "0",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val topics: Array[String] = Array("TEMPERATURE")
    val stream: org.apache.spark.streaming.dstream.InputDStream[ConsumerRecord[String, String]]
    = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))

    val connector = CassandraConnector.apply(sc)

    try {
      val session = connector.openSession
      try {
        session.execute("DROP KEYSPACE IF EXISTS readings")
        session.execute("CREATE KEYSPACE readings WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}")
        session.execute("CREATE TABLE readings.temperatures (" +
          "id UUID PRIMARY KEY, " +
          "device_id INT, " +
          "device_serial_number TEXT, " +
          "device_type TEXT, " +
          "unit TEXT, " +
          "periodicity TEXT, " +
          "timeOfReading BIGINT, " +
          "reading TEXT)")
      } finally if (session != null) session.close()
    }

    val rdd = sc.cassandraTable("readings", "temperatures")
    val file_collect = rdd.collect().take(100)
    file_collect.foreach(println(_))

    stream.foreachRDD { rdd =>
      System.out.println("--- New RDD with " + rdd.partitions.length + " partitions and " + rdd.count + " records")
      val strings = rdd.map(record => record.value()).collect();
      strings.foreach(temperatureString => {
        val temperature = Json.fromJson[Temperature](Json.parse(temperatureString)).get
        val collection = sc.parallelize(Seq((
          UUID.randomUUID(),
          temperature.deviceId,
          temperature.deviceSerialNumber,
          temperature.deviceType,
          temperature.unit,
          temperature.periodicity,
          temperature.timeOfReading,
          temperature.reading
        )))
        collection.saveToCassandra("readings", "temperatures",
          SomeColumns("id", "device_id", "device_serial_number", "device_type", "unit", "periodicity", "timeOfReading", "reading"))
      })
    }

    println("***************************************************************************************************************")
    println(stream.id)
    println(stream.count())
    println("***************************************************************************************************************")

    streamingContext.start
    streamingContext.awaitTermination()
  }
}