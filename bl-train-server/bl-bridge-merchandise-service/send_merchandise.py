#!/usr/bin/env python
import pika
import sys

credentials = pika.PlainCredentials('test', 'test')

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost', port='5673', credentials=credentials, virtual_host='bl-merchandise-vh'))
channel = connection.channel()
channel.queue_declare("bl-merchandise-queue", durable=True)
channel.exchange_declare(exchange='bl-merchandise-exchange', exchange_type='fanout', durable=True)



message = ' '.join(sys.argv[1:]) or "info: Hello World!"
channel.basic_publish(exchange='bl-merchandise-exchange', routing_key='', body=message)
print(" [x] Sent %r" % message)
connection.close()
