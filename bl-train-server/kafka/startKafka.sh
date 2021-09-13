#!/usr/bin/env bash
sleep 4
/usr/bin/kafka-server-start /opt/kafka/config/server0.properties &
/usr/bin/kafka-server-start /opt/kafka/config/server1.properties &
/usr/bin/kafka-server-start /opt/kafka/config/server2.properties &
/usr/bin/kafka-server-start /opt/kafka/config/server3.properties &
sleep 4
/usr/bin/kafka-topics --create --zookeeper bl_train_01_zookeeper_server:2181 --replication-factor 2 --partitions 4 --topic PASSENGER
