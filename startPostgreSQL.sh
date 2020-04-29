#!/usr/bin/env bash

docker-compose down

cd bl-demo-server/docker-psql

docker-compose down

docker-compose up -d --build --remove-orphans

cd ..
