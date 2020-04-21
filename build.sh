#!/usr/bin/env bash

docker-compose down

docker-compose up -d --build --remove-orphans

python bl-demo-server/bl-demo-launcer/lauch_demo_server.py
