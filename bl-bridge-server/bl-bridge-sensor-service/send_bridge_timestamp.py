#!/usr/bin/env python
import json
import sys

import pika


def sendSignal(host, data):
    data_json = json.dumps(data)
    credentials = pika.PlainCredentials('test', 'test')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=host, port=5674, credentials=credentials, virtual_host='bl_bridge_sensor_vh'))
    channel = connection.channel()
    channel.queue_declare("bl_bridge_sensor_queue", durable=True)
    channel.exchange_declare(exchange='bl_bridge_sensor_exchange', exchange_type='fanout', durable=True)
    message = ' '.join(sys.argv[1:]) or data_json
    channel.basic_publish(exchange='bl_bridge_sensor_exchange', routing_key='', body=message)
    print(" [x] Sent %r" % message)
    connection.close()
