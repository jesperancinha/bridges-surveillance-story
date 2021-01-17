#!/usr/bin/env bash

docker-compose down

cd bl-central-server/cassandra || exit

docker-compose down

docker-compose up -d --build --remove-orphans

cd ..
