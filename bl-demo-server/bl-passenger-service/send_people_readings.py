#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from time import sleep

from kafka import KafkaProducer


def send_people(host, passengers):
    success = False
    while not success:
        try:
            json_message = json.dumps(passengers)
            print("🧍🧍 Sending passengers..." + host)
            producer = KafkaProducer(bootstrap_servers=[host + ':9098', host + ':9099', host + ':9100', host + ':9101'])
            # print("🧍🧍 Sending " + str(json_message) + " ...")
            producer.send('PASSENGER', json_message)
            # print("🧍🧍 Sent " + json_message + "!")
            producer.flush()
            success = True
        except Exception as err:
            print("🔴 2Passenger service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            print("🔴 " + str(err))
            sleep(10)
    print("🧍🧍 Passengers sent!")


if __name__ == '__main__':
    send_meter('127.0.0.1', {})
