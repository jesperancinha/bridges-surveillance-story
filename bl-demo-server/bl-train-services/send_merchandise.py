# !/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import sys

import pika


def send_merchandise(host, data):
    data_json = json.dumps(data)
    credentials = pika.PlainCredentials('test', 'test')
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=host, port=5673, credentials=credentials, virtual_host='bl_train_01_merchandise_vh'))
    channel = connection.channel()
    channel.queue_declare("bl_train_01_merchandise_queue", durable=True)
    channel.exchange_declare(exchange='bl_train_01_merchandise_exchange', exchange_type='fanout', durable=True)
    message = ' '.join(sys.argv[1:]) or data_json
    channel.basic_publish(exchange='bl_train_01_merchandise_exchange', routing_key='', body=message)
    print("🚂 📦 Sent %r" % message)
    connection.close()


if __name__ == '__main__':
    send_merchandise('localhost',
                     {"id": 1, "supplierId": "64985638639853"})
