#!/usr/bin/env bash

rabbitmq-server -detached

rabbitmqctl start_app

sleep 1

rabbitmqctl await_startup

rabbitmqctl start_app

sleep 1

rabbitmqctl await_startup

rabbitmq-plugins enable rabbitmq_federation

rabbitmq-plugins enable rabbitmq_federation_management

curl -S http://localhost:15672/cli/rabbitmqadmin > /usr/local/bin/rabbitmqadmin

chmod +x /usr/local/bin/rabbitmqadmin

rabbitmqctl add_user test test

rabbitmqctl set_user_tags test administrator

rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

federate(){
    rabbitmqctl add_vhost bl_$1_vh

    rabbitmqctl set_permissions -p bl_$1_vh test ".*" ".*" ".*"

    rabbitmqadmin -u test -p test -V bl_$1_vh declare exchange name=bl_$1_exchange type=fanout

    rabbitmqadmin -u test -p test -V bl_$1_vh declare queue name=bl_$1_queue

    rabbitmqadmin -u test -p test -V bl_$1_vh declare binding source=bl_$1_exchange destination=bl_$1_queue

    rabbitmqctl set_parameter -p bl_$1_vh federation-upstream bl_$1_upstream '{"uri":"amqp://test:test@bl_'$2'_server:5672/bl_'$1'_vh","expires":3600000}'

    rabbitmqctl set_policy -p bl_$1_vh --apply-to all bl_$1_policy ".*$1.*" '{"federation-upstream-set":"all"}'
}

federate train_01_merchandise train_01_rabbitmq
federate train_01_sensor train_01_rabbitmq
federate bridge_01_sensor bridge_01_rabbitmq

tail -f /dev/null