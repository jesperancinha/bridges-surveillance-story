#!/usr/bin/env python
import pika
import sys

credentials = pika.PlainCredentials('test', 'test')

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost', port='5673', credentials=credentials, virtual_host='bl_merchandise_vh'))
channel = connection.channel()
channel.queue_declare("bl_merchandise_queue", durable=True)
channel.exchange_declare(exchange='bl_merchandise_exchange', exchange_type='fanout', durable=True)

message = ' '.join(sys.argv[1:]) or "info: Hello World!"
channel.basic_publish(exchange='bl_merchandise_exchange', routing_key='', body=message)
print(" [x] Sent %r" % message)
connection.close()
