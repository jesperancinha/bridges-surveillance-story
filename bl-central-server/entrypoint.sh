#!/usr/bin/env bash

rabbitmq-server -detached

rabbitmqctl await_startup

rabbitmqctl add_user test test

rabbitmqctl set_user_tags test administrator

rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

rabbitmqctl add_vhost bl_merchandise_vh

rabbitmqctl set_permissions -p bl_merchandise_vh test ".*" ".*" ".*"

rabbitmqadmin -u test -p test -V bl_merchandise_vh declare exchange name=bl_merchandise_exchange type=fanout

rabbitmqadmin -u test -p test -V bl_merchandise_vh declare queue name=bl_merchandise_queue

rabbitmqadmin -u test -p test -V bl_merchandise_vh declare binding source=bl_merchandise_exchange destination=bl_merchandise_queue

rabbitmqctl set_parameter -p bl_merchandise_vh federation-upstream bl_merchandise_upstream '{"uri":"amqp://test:test@localhost:5673/bl_merchandise_vh","expires":3600000}'

rabbitmqctl set_policy -p bl_merchandise_vh --apply-to all bl_merchandise_policy ".*merchandise.*" '{"federation-upstream-set":"all"}'

tail -f /dev/null