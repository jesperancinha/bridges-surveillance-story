#!/usr/bin/env bash
brew services stop cassandra
brew services stop mosquitto
brew services stop rabbitmq
brew services stop mongodb-community
kafka-server-stop
zookeeper-server-stop
zkServer stop
rabbitmqctl stop

processAString=$(ps -fx | grep -e "bl-meters-readings-service.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber}

processAString=$(ps -fx | grep -e "bl-passengers-readings-service.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber}


