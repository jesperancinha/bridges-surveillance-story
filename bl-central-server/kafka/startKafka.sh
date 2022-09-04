#!/usr/bin/env bash
/usr/bin/zookeeper-server-start /etc/kafka/zookeeper.properties &
sleep 4
/usr/bin/kafka-server-start /opt/kafka/config/server0.properties &
/usr/bin/kafka-server-start /opt/kafka/config/server1.properties &
sleep 4
/usr/bin/kafka-topics --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 2 --topic TEMPERATURE
/usr/bin/kafka-topics --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 2 --topic HUMIDITY
/usr/bin/kafka-topics --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 2 --topic WINDSPEED
/usr/bin/kafka-topics --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 2 --topic WINDDIRECTION
/usr/bin/kafka-topics --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 4 --topic PASSENGER
