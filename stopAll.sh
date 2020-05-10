#!/usr/bin/env bash

./stopMacOs.sh

docker stop bl-bridge-server-container

docker rm bl-bridge-server-container

docker-compose down

docker stop bl-central-psql_postgres_1

cd bl-central-server/bl-central-psql

docker-compose down

cd ../..

cd bl-central-server/cassandra

docker-compose down

cd ../..

