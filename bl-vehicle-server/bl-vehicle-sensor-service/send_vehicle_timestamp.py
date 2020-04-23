#!/usr/bin/env python
import json
import sys

import pika


def send_signal(host, data):
    data_json = json.dumps(data)
    credentials = pika.PlainCredentials('test', 'test')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=host, port=5675, credentials=credentials, virtual_host='bl_vehicle_01_sensor_vh'))
    channel = connection.channel()
    channel.queue_declare("bl_vehicle_01_sensor_queue", durable=True)
    channel.exchange_declare(exchange='bl_vehicle_01_sensor_exchange', exchange_type='fanout', durable=True)
    message = ' '.join(sys.argv[1:]) or data_json
    channel.basic_publish(exchange='bl_vehicle_01_sensor_exchange', routing_key='', body=message)
    print(" [x] Sent %r" % message)
    connection.close()
