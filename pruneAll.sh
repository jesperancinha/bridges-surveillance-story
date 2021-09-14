#!/usr/bin/env bash

./stopMacOs.sh

cd bl-central-psql

docker-compose down

cd ..

docker-compose down

docker stop bl-central-psql_postgres_1

docker rm bl-central-psql_postgres_1

docker rmi bridge-logistics_bl_train_01_rabbitmq_server

docker rmi bridge-logistics_bl_central_kafka_server

docker rmi bridge-logistics_bl_train_01_zookeeper_server

docker rmi bridge-logistics_bl_vehicle_01_server

docker rmi bridge-logistics_bl_central_server

docker rmi bridge-logistics_postgres
