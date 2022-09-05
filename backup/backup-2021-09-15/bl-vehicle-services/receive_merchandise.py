#!/usr/bin/env python
import pika

credentials = pika.PlainCredentials('test', 'test')

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost', port=5672, credentials=credentials, virtual_host='bl-vehicle-01-merchandise-vh'))
channel = connection.channel()

channel.exchange_declare(exchange='bl-vehicle-01-merchandise-exchange', exchange_type='fanout', durable=True)

# IF YOU WANT TO CREATE A NEW QUEUE ONLY!
# result = channel.queue_declare(queue='bl-vehicle-01-merchandise-queue', exclusive=True)

queue_name = 'bl-vehicle-01-merchandise-queue'

channel.queue_bind(exchange='bl-vehicle-01-merchandise-exchange', queue='bl-vehicle-01-merchandise-queue')

print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
    print(" [x] %r" % body)


channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()
