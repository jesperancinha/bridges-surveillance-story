#!/usr/bin/env bash

docker-compose down

docker stop docker-psql_postgres_1

cd docker-psql

docker-compose down

cd ..

./stopMacOs.sh
