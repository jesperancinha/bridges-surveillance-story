#!/bin/sh

node bl-bridge-humidity-mqtt/dist/app.js bl_bridge_01_mosquitto_server &

tail -f /dev/null

