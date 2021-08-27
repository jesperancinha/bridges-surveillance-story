#!/usr/bin/env bash
sleep 4
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 3 --topic PASSENGER
sleep 4
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server0.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server1.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server2.properties &
/opt/bitnami/kafka/bin/kafka-server-start.sh /opt/bitnami/kafka/config/server3.properties &
