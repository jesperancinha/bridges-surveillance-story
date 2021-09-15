#!/usr/bin/env bash

java -jar -Dspring.profiles.active=demo bl-sensor-data-collector.jar &

java -jar -Dspring.profiles.active=demo bl-merchandise-data-collector.jar &

for counter in $(seq 1 30); do
    echo -ne "Starting app in $counter seconds ...\r"
    sleep 1
done

java -jar -Dspring.profiles.active=demo bl-web-app.jar &

tail -f /dev/null
