#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
import sys

import pika


def send_signal(host, data):
    data_json = json.dumps(data)
    credentials = pika.PlainCredentials('test', 'test')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=host, port=5673, credentials=credentials, virtual_host='bl_train_01_sensor_vh', socket_timeout=20, blocked_connection_timeout=20, stack_timeout=20))
    channel = connection.channel()
    channel.queue_declare("bl_train_01_sensor_queue", durable=True)
    channel.exchange_declare(exchange='bl_train_01_sensor_exchange', exchange_type='fanout', durable=True)
    message = ' '.join(sys.argv[1:]) or data_json
    channel.basic_publish(exchange='bl_train_01_sensor_exchange', routing_key='', body=message)
    print("🚂 ⏲ Sent %r" % message)
    connection.close()
