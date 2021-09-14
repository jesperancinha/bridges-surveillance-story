#!/bin/sh

node bl-bridge-humidity-mqtt/dist/app.js bl_bridge_01_mosquitto_server &

node bl-bridge-temperature-coap/dist/app.js &

tail -f /dev/null

