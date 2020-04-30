#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json

from coapthon.client.helperclient import HelperClient


def send_meter(host, data):
    port = 5683
    path = ''

    client = HelperClient(server=(host, port))
    dumps = json.dumps(data)
    print("🌡 Sending reading" + dumps)
    response = client.post(path, dumps)
    print("🌡 Result" + str.join(" ", response.pretty_print().splitlines()))
    client.stop()


if __name__ == '__main__':
    send_meter('127.0.0.1', {})
