 #!/usr/bin/env bash

/usr/local/etc/kafka/bin/kafka-server-start.sh /usr/local/etc/kafka/config/server0.properties &
/usr/local/etc/kafka/bin/kafka-server-start.sh /usr/local/etc/kafka/config/server1.properties &
/usr/local/etc/kafka/bin/kafka-server-start.sh /usr/local/etc/kafka/config/server2.properties &
/usr/local/etc/kafka/bin/kafka-server-start.sh /usr/local/etc/kafka/config/server3.properties &

echo -e '\e[32m'Sleeping...'\e[39m'
sleep 4
/usr/local/etc/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 4 --partitions 12 --topic PASSENGER
