#!/usr/bin/env bash

docker-compose down

cd docker-psql

docker-compose down

docker-compose up -d --build --remove-orphans

cd ..
