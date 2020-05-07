#!/usr/bin/env bash

java -jar --enable-preview -Dspring.profiles.active=demo bl-web-app.jar &

sleep 60

java -jar --enable-preview -Dspring.profiles.active=demo bl-sensor-data-collector.jar &

java -jar --enable-preview -Dspring.profiles.active=demo bl-merchandise-data-collector.jar &

tail -f /dev/null
