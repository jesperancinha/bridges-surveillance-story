#!/usr/bin/env bash

./stopMacOs.sh

cd bl-central-psql

docker-compose down

cd .. || exit

docker-compose down

docker stop bl-central-psql_postgres_1

docker rm bl-central-psql_postgres_1

docker rmi bridge-logistics-bl-train-01-rabbitmq-server

docker rmi bridge-logistics-bl-central-kafka-server

docker rmi bridge-logistics-bl-train-01-zookeeper-server

docker rmi bridge-logistics-bl-vehicle-01-server

docker rmi bridge-logistics-bl-central-server

docker rmi bridge-logistics_postgres
