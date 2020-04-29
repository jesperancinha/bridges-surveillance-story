#!/usr/bin/env bash

docker-compose down

docker stop docker-psql_postgres_1

cd bl-central-server/bl-sensor-data-collector

mvn clean install -Pdemo -DskipTests

cd ..

cd bl-web-app

mvn clean install -Pdemo -DskipTests

cd ../..

cd bl-bridge-server/bl-bridge-humidity-coap

npm start build

cd ../..

docker-compose up -d --build --remove-orphans
