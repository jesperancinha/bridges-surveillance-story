#!/usr/bin/env bash

mkdir -p target

processAString=$(ps -fx | grep -e "bl-meters-readings-service.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber}

processAString=$(ps -fx | grep -e "bl-passengers-readings-service.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber}

rm target/**
cp bl-central-server/bl-meters-readings-service/target/bl-meters-readings-service-*dependencies.jar target/bl-meters-readings-service.jar

cp bl-central-server/bl-passengers-readings-service/target/bl-passengers-readings-service-*dependencies.jar target/bl-passengers-readings-service.jar

java -jar target/bl-meters-readings-service.jar &

java -jar target/bl-passengers-readings-service.jar &
