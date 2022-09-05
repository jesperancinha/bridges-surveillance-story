#!/usr/bin/env bash

rabbitmq-server -detached

rabbitmqctl start_app

rabbitmqctl await_startup

#rabbitmq-plugins enable rabbitmq_management

#rabbitmqctl await_startup

#rabbitmq-plugins enable --offline rabbitmq_management

rabbitmqctl start_app

rabbitmqctl await_startup

rabbitmq-plugins enable rabbitmq_federation

rabbitmq-plugins enable rabbitmq_federation_management

#rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

curl -S http://localhost:15672/cli/rabbitmqadmin > /usr/local/bin/rabbitmqadmin

chmod +x /usr/local/bin/rabbitmqadmin



rabbitmqctl add_user test test

rabbitmqctl set_user_tags test administrator

rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

federate(){
    rabbitmqctl add_vhost bl-"$1"-vh

    rabbitmqctl set_permissions -p bl-"$1"-vh test ".*" ".*" ".*"

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare exchange name=bl-"$1"-exchange type=fanout

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare queue name=bl-"$1"-queue

    rabbitmqadmin -u test -p test -V bl-"$1"-vh declare binding source=bl-"$1"-exchange destination=bl-"$1"-queue

    rabbitmqctl set_parameter -p bl-"$1"-vh federation-upstream bl-"$1"-upstream '{"uri":"amqp://test:test@bl-'"$2"'-server:5672/bl-'"$1"'-vh","expires":3600000}'

    rabbitmqctl set_policy -p bl-"$1"-vh --apply-to all bl-"$1"-policy ".*$1.*" '{"federation-upstream-set":"all"}'
}

federate train-01-merchandise train-01
federate train-01-sensor train-01
federate bridge-01-sensor bridge-01

tail -f /dev/null