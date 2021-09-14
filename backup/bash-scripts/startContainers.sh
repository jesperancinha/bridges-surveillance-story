#!/usr/bin/env bash

./stopAll.sh

docker-compose down

docker stop bl-central-psql_postgres_1

#./build.sh

docker-compose up -d --build --remove-orphans
