#!/usr/bin/env bash

docker-compose down

docker stop docker-psql_postgres_1

docker-compose up -d --build --remove-orphans