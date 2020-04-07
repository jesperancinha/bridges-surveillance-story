#!/usr/bin/env bash

rabbitmq-server -detached

rabbitmqctl await_startup

rabbitmqctl add_user test test

rabbitmqctl set_user_tags test administrator

rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

tail -f /dev/null