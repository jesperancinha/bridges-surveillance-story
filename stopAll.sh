#!/usr/bin/env bash

docker stop bl-bridge-server-container

docker rm bl-bridge-server-container

docker-compose down

docker stop docker-psql_postgres_1

cd bl-central-server/docker-psql

docker-compose down

cd ../..

cd bl-central-server/cassandra

docker-compose down

cd ../..

./stopMacOs.sh
