#!/usr/bin/env python
# -*- coding: utf-8 -*-
from paho.mqtt import publish


def send_meter(host, data):
    publish.single("moisture", str(data), hostname=host)


if __name__ == '__main__':
    send_meter('localhost', {})
