#!/usr/bin/env bash

rabbitmq-plugins enable rabbitmq_management

rabbitmq-server -detached

rabbitmqctl await_startup

curl -S http://localhost:15672/cli/rabbitmqadmin > /usr/local/bin/rabbitmqadmin

chmod +x /usr/local/bin/rabbitmqadmin

ln -s /usr/bin/python3 /usr/bin/python

rabbitmqctl add_user test test

rabbitmqctl set_user_tags test administrator

rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

function sourceQueue(){
    rabbitmqctl add_vhost bl_$1_vh

    rabbitmqctl set_permissions -p bl_$1_vh test ".*" ".*" ".*"

    rabbitmqadmin -u test -p test -V bl_$1_vh declare exchange name=bl_$1_exchange type=fanout

    rabbitmqadmin -u test -p test -V bl_$1_vh declare queue name=bl_$1_queue

    rabbitmqadmin -u test -p test -V bl_$1_vh declare binding source=bl_$1_exchange destination=bl_$1_queue
}

sourceQueue bridge_01_sensor

tail -f /dev/null
