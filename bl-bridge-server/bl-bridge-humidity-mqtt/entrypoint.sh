#!/bin/sh

node bl-bridge-humidity-mqtt/dist/app.js bl-bridge-01-mosquitto-server &

tail -f /dev/null

