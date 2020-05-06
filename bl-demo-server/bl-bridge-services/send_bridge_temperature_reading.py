#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from time import sleep

from coapthon.client.helperclient import HelperClient


def send_meter(host, data):
    port = 5683
    path = ''

    client = HelperClient(server=(host, port))
    dumps = json.dumps(data)
    print("🌡 Sending reading" + dumps)
    success = False
    while not success:
        try:
            response = client.post(path, dumps, timeout=5)
            print("🌡 Result" + str.join(" ", response.pretty_print().splitlines()))
            client.stop()
            success = True
        except Exception as err:
            print("🔴 Temperature CoAP service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            print("🔴 " + str(err))
            sleep(10)


if __name__ == '__main__':
    send_meter('127.0.0.1', {})
