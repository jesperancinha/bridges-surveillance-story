#!/usr/bin/env bash
brew services stop cassandra
brew services stop mosquitto
brew services stop rabbitmq
brew services stop mongodb-community
kafka-server-stop
zookeeper-server-stop
zkServer stop
rabbitmqctl stop

