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
    print("🌡 Sending temperature reading" + dumps)
    success = False
    tries = 5
    while not success and tries > 0:
        try:
            response = client.post(path, dumps, timeout=5)
            print("🌡 Result" + str.join(" ", response.pretty_print().splitlines()))
            client.stop()
            success = True
        except Exception as err:
            tries -= 1
            print("🔴 Temperature CoAP service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            if not str(err).strip() == "":
                print("🔴 " + str(err))
            sleep(10)
    print("🌡 Sent temperature reading" + dumps)


if __name__ == '__main__':
    for i in range(0, 100):
        send_meter('127.0.0.1', {"Dsfdsfdsbfkjdwbfjklasbflkdsklfsdklfdsklfhsdhkf": "sdfkidsfhjdsifhjiosdjfiosdjiofjioejfiojqeiofjioewjfjiweijfiowejoi"})
