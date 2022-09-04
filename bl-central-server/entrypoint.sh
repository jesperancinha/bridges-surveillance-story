#!/usr/bin/env sh

java -jar -Dspring.profiles.active=demo bl-sensor-data-collector.jar &

java -jar -Dspring.profiles.active=demo bl-merchandise-data-collector.jar &

java -jar -Dspring.profiles.active=demo bl-web-app.jar &

tail -f /dev/null
