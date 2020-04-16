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

federate(){
    rabbitmqctl add_vhost bl_$1_vh

    rabbitmqctl set_permissions -p bl_$1_vh test ".*" ".*" ".*"

    rabbitmqadmin -u test -p test -V bl_$1_vh declare exchange name=bl_$1_exchange type=fanout

    rabbitmqadmin -u test -p test -V bl_$1_vh declare queue name=bl_$1_queue

    rabbitmqadmin -u test -p test -V bl_$1_vh declare binding source=bl_$1_exchange destination=bl_$1_queue

    rabbitmqctl set_parameter -p bl_$1_vh federation-upstream bl_$1_upstream '{"uri":"amqp://test:test@bl_train_server:5672/bl_'$1'_vh","expires":3600000}'

    rabbitmqctl set_policy -p bl_$1_vh --apply-to all bl_$1_policy ".*$1.*" '{"federation-upstream-set":"all"}'
}

federate merchandise
federate sensor

tail -f /dev/null