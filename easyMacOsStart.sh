#!/usr/bin/env bash
kafka-server-stop
zookeeper-server-stop
zkServer stop

rm -rf /usr/local/var/lib/kafka-logs**
rm -rf /tmp/zookeeper

./copyFiles.sh

#zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties &
zkServer start /usr/local/etc/kafka/zookeeper.properties &
kafka-server-start /usr/local/etc/kafka/server0.properties &
kafka-server-start /usr/local/etc/kafka/server1.properties &
kafka-server-start /usr/local/etc/kafka/server2.properties &

echo -e '\e[32m'Sleeping...'\e[39m'
sleep 4
kafka-topics --create --zookeeper localhost:2181 --replication-factor 2 --partitions 3 --topic TEMPERATURE
kafka-topics --create --zookeeper localhost:2181 --replication-factor 2 --partitions 3 --topic MOISTURE
kafka-topics --create --zookeeper localhost:2181 --replication-factor 2 --partitions 3 --topic WINDSPEED
kafka-topics --create --zookeeper localhost:2181 --replication-factor 2 --partitions 3 --topic WINDDIRECTION

echo -e '\e[32m'Check your Kafka logs here: /usr/local/var/lib/kafka-logs '\e[39m'
echo -e '\e[32m'Check your Zookeeper logs here: /tmp/zookeeper '\e[39m'
echo -e '\e[32m'Check your Zookeeper service logs here: /usr/local/var/log/zookeeper/zookeeper.log '\e[39m'
