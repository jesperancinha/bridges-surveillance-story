#!/usr/bin/env bash

processAString=$(ps -fx | grep -e "bl-meters-readings-service-jar-with-dependencies.jar" | grep -v "grep")
processNumber="$(echo "${processAString}" | cut -d' ' -f2)"
echo "${processNumber}"
kill -9 "${processNumber}" &

processAString=$(ps -fx | grep -e "bl-passengers-readings-service-jar-with-dependencies.jar" | grep -v "grep")
processNumber="$(echo "${processAString}" | cut -d' ' -f2)"
echo "${processNumber}"
kill -9 "${processNumber}" &
