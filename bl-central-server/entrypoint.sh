#!/usr/bin/env bash

sleep 60

java -jar -Dspring.profiles.active=demo bl-web-app.jar &

sleep 60

java -jar -Dspring.profiles.active=demo bl-sensor-data-collector.jar &

java -jar -Dspring.profiles.active=demo bl-merchandise-data-collector.jar &

tail -f /dev/null
