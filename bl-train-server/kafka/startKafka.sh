#!/usr/bin/env bash
sleep 4
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server0.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server1.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server2.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server3.properties &
sleep 4
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper bl_train_01_zookeeper_server:2181 --replication-factor 2 --partitions 4 --topic PASSENGER
