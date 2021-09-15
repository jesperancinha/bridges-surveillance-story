#!/usr/bin/env bash

processAString=$(ps -fx | grep -e "bl-meters-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber} &

processAString=$(ps -fx | grep -e "bl-passengers-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar" | grep -v "grep")
processNumber="$(echo ${processAString} | cut -d' ' -f2)"
echo ${processNumber}
kill -9 ${processNumber} &


