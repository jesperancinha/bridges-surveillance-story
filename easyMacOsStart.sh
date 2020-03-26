#!/usr/bin/env bash
kafka-server-stop
zookeeper-server-stop
zkServer stop

rm -rf /usr/local/var/lib/kafka-logs
rm -rf /tmp/zookeeper
#zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties &
zkServer start
kafka-server-start /usr/local/etc/kafka/server.properties &

kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TEMPERATURE
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic MOISTURE
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic WINDSPEED
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic WINDDIRECTION

echo -e '\e[32m'Check your Kafka logs here: /usr/local/var/lib/kafka-logs '\e[39m'
echo -e '\e[32m'Check your Zookeeper logs here: /tmp/zookeeper '\e[39m'
echo -e '\e[32m'Check your Zookeeper service logs here: /usr/local/var/log/zookeeper/zookeeper.log '\e[39m'
