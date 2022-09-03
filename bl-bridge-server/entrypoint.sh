#!/bin/sh

for counter in $(seq 1 30); do
    echo -ne "Starting app in $counter seconds ...\r"
    sleep 1
done

node bl-bridge-humidity-mqtt/dist/src/app.js bl_central_kafka_server &

node bl-bridge-temperature-coap/dist/src/app.js bl_central_kafka_server &

tail -f /dev/null

