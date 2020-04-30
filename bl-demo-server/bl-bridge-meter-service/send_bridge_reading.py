#!/usr/bin/env python
import json

from coapthon.client.helperclient import HelperClient


def send_meter(host, data):
    port = 5683
    path = ''

    client = HelperClient(server=(host, port))
    dumps = json.dumps(data)
    response = client.post(path, dumps)
    print(response.pretty_print())
    client.stop()


if __name__ == '__main__':
    send_signal('127.0.0.1', {})
