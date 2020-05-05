#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from time import sleep

from kafka import KafkaProducer


def send_people(host, passengers):
    success = False
    while not success:
        try:
            producer = KafkaProducer(bootstrap_servers='localhost:9098')
            print("🧍🧍 Sending passengers...")
            for passenger in passengers:
                success = False
                while not success:
                    try:
                        json_message = json.dumps(passenger)
                        # print("🧍🧍 Sending " + json_message + " ...")
                        producer.send('PASSENGER', json_message)
                        # print("🧍🧍 Sent " + json_message + "!")
                        success = True
                    except Exception as err:
                        print(err)
                        print("🔴 Passenger service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
                        print("🔴 " + str(err))
                        sleep(10)
            success = True
        except Exception as err:
            print("🔴 Passenger service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            print("🔴 " + str(err))
            sleep(10)


print("🧍🧍 Passengers sent!")

if __name__ == '__main__':
    send_meter('127.0.0.1', {})
