#!/usr/bin/env bash
sleep 4
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server0.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server1.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server2.properties &
sleep 4
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper bl_bridge_01_zookeeper_server:2181 --replication-factor 2 --partitions 3 --topic TEMPERATURE
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper bl_bridge_01_zookeeper_server:2181 --replication-factor 2 --partitions 3 --topic HUMIDITY
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper bl_bridge_01_zookeeper_server:2181 --replication-factor 2 --partitions 3 --topic WINDSPEED
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper bl_bridge_01_zookeeper_server:2181 --replication-factor 2 --partitions 3 --topic WINDDIRECTION
