#!/usr/bin/env python
import pika

credentials = pika.PlainCredentials('test', 'test')

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost', port=5672, credentials=credentials, virtual_host='bl_train_01_merchandise_vh'))
channel = connection.channel()

channel.exchange_declare(exchange='bl_train_01_merchandise_exchange', exchange_type='fanout', durable=True)

# IF YOU WANT TO CREATE A NEW QUEUE ONLY!
# result = channel.queue_declare(queue='bl_train_01_merchandise_queue', exclusive=True)

queue_name = 'bl_train_01_merchandise_queue'

channel.queue_bind(exchange='bl_train_01_merchandise_exchange', queue='bl_train_01_merchandise_queue')

print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
    print(" [x] %r" % body)


channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()
