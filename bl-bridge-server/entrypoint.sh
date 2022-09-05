#!/bin/sh

node bl-bridge-humidity-mqtt/dist/src/app.js bl-central-kafka-server &

node bl-bridge-temperature-coap/dist/src/app.js bl-central-kafka-server &

tail -f /dev/null

