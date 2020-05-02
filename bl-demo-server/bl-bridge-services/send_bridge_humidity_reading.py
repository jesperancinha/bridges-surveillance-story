#!/usr/bin/env python
# -*- coding: utf-8 -*-
import json

from paho.mqtt import publish


def send_meter(host, data):
    test_data = json.dumps(data)
    print("💧 Sending " + test_data)
    publish.single("humidity", test_data, hostname=host)
    print("💧 Sent " + test_data)


if __name__ == '__main__':
    send_meter('127.0.0.1', {})
