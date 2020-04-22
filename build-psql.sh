#!/usr/bin/env bash

cd docker-psql

docker-compose down

docker-compose up -d --build --remove-orphans

cd ..
