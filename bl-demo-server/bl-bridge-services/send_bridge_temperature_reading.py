#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json
from time import sleep

from aiocoap import *


async def send_meter(host, data):
    port = 5683
    context = await Context.create_client_context()

    dumps = json.dumps(data)
    print("🌡 Sending temperature reading" + dumps)
    success = False
    tries = 5
    while not success and tries > 0:
        try:
            request = Message(code='PUT', payload=dumps, uri="coap://{0}:{1}".format(host, port))
            response = await context.request(request).response
            print("🌡 Result" + str.join(" ", response.pretty_print().splitlines()))
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
