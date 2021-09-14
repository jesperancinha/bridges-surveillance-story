#!/usr/bin/env sh

rm -rf /home/appuser/kafka-logs/*

./startKafka.sh &

tail -f /dev/null
