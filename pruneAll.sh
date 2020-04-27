#!/usr/bin/env bash

./stopMacOs.sh

cd docker-psql

docker-compose down

cd ..

docker-compose down

docker stop docker-psql_postgres_1

docker rm docker-psql_postgres_1

docker rmi bridge-logistics_bl_train_01_server

docker rmi bridge-logistics_bl_vehicle_01_server

docker rmi bridge-logistics_bl_central_server

docker rmi bridge-logistics_postgres
