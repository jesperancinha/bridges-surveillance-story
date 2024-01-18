# bl-readings-service

## Starting Spark

```bash
mvn scala:run -DmainClass=org.jesperancinha.bridgelogistics.readings.ReadingsLauncher
```
## Start Jar

```shell
java --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED --add-exports=java.base/sun.nio.ch=ALL-UNNAMED -jar target/bl-passengers-readings-service-jar-with-dependencies.jar
```

## Installing cassandra

```bash
brew install cassandra
```

## Starting cassandra
Note that some cassandra versions only seem to run well with Java 8
```bash
cassandra
```

## Cassandra command line

```bash
cqlsh
```

```genericsql
DESCRIBE TABLES
SELECT *  FROM java_api.products;

```
## Hints & Tricks

```bash
brew install sbt
brew upgrade sbt
```
## References

-   [Reading configurations in Scala](https://medium.com/@ramkarnani24/reading-configurations-in-scala-f987f839f54d)
-   [Accessing Cassandra from Spark in Java](https://www.datastax.com/blog/2014/08/accessing-cassandra-spark-java)
-   [Step by Step of Configuring Apache Spark to Connect with Cassandra](https://chongyaorobin.wordpress.com/2015/07/16/step-by-step-of-how-to-configure-apache-spark-to-connect-with-cassandra/)
-   [Install All the THINGS! But especially Apache Cassandra, Apache Spark and Jupyter](https://www.datastax.com/blog/2019/04/install-all-things-especially-apache-cassandra-apache-spark-and-jupyter)
-   [Getting Started with Cassandra and Spark](https://www.codementor.io/@sheena/installing-cassandra-spark-linux-debian-ubuntu-14-du107vbhx)
-   [Structured Streaming + Kafka Integration Guide (Kafka broker version 0.10.0 or higher](https://spark.apache.org/docs/latest/structured-streaming-kafka-integration.html)
-   [Apache Spark - Lightning-fast unified analytics engine](https://spark.apache.org/)
-   [Apache Spark - Monitoring and Instrumentation](https://spark.apache.org/docs/latest/monitoring.html)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
