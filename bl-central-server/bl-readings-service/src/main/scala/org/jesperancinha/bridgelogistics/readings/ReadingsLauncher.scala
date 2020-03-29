package org.jesperancinha.bridgelogistics.readings

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

object ReadingsLauncher extends App {

  override def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf
    sparkConf.setAppName("WordCountingApp")
    sparkConf.setMaster("local[*]")

    val streamingContext = new org.apache.spark.streaming.StreamingContext(sparkConf, Durations.seconds(1))

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

    val maps = stream.map(record => (record.key, record.value))

    stream.foreachRDD { rdd =>
      System.out.println("--- New RDD with " + rdd.partitions.length + " partitions and " + rdd.count + " records")
      rdd.foreach(record =>
        println(record.value())
      )
    }

    println("***************************************************************************************************************")
    println(stream.id)
    println(stream.count())
    println("***************************************************************************************************************")

    streamingContext.start
    streamingContext.awaitTermination()
  }
}