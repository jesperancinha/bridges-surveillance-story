#!/usr/bin/env python
import json
import time
import paho.mqtt.client as mqtt

def on_message(client, userdata, message):
    print("message received ", str(message.payload.decode("utf-8")))
    print("message topic=", message.topic)
    print("message qos=", message.qos)
    print("message retain flag=", message.retain)


def send_meter(host, data):
    client = mqtt.Client("P1")
    client.on_message=on_message
    client.connect(host)
    client.loop_start()
    client.subscribe("moisture")
    client.publish("moisture", str(data))
    time.sleep(4)
    client.loop_stop()


if __name__ == '__main__':
    send_meter('127.0.0.1', {})
