#!/bin/sh

node bl-bridge-humidity-mqtt/dist/app.js bl_bridge_01_kafka_server &

node bl-bridge-temperature-coap/dist/app.js bl_bridge_01_kafka_server &

tail -f /dev/null

