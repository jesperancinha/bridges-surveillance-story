#!/usr/bin/env bash
rabbitmqctl await_startup

rabbitmq-server -detached

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
    rabbitmqctl add_vhost bl-"$1"-vh

    rabbitmqctl set_permissions -p bl-"$1"-vh test ".*" ".*" ".*"

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare exchange name=bl-"$1"-exchange type=fanout

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare queue name=bl-"$1"-queue

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare binding source=bl-"$1"-exchange destination=bl-"$1"-queue
}

sourceQueue train-01-merchandise
sourceQueue train-01-sensor

tail -f /dev/null
