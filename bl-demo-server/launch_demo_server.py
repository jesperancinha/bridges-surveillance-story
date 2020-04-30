# -*- coding: utf-8 -*-
import os
import sys
from multiprocessing import Process
from time import sleep

import requests

sys.path.insert(1, os.path.abspath('bl-core-service'))
sys.path.insert(2, os.path.abspath('../bl-core-service'))

from launch_start_train import start_train
from launch_start_vehicle import start_vehicle
from launch_start_bridge_meters import start_bridge_meters


def train_simulation():
    while True:
        start_train('localhost')


def vehicle_simulation():
    while True:
        start_vehicle('localhost')


def bridge_meter_simulation():
    while True:
        start_bridge_meters('127.0.0.1')


train_simulation_process = Process(target=train_simulation)
vehicle_simulation_process = Process(target=vehicle_simulation)
bridge_meters_simulation_process = Process(target=bridge_meter_simulation)

train_simulation_process.start()
vehicle_simulation_process.start()
bridge_meters_simulation_process.start()

URL_OPEN = "http://localhost:9000/api/bridge/logistics/schedules/open/52.347293/4.912372"

while True:
    try:
        print("Simulation ongoing. Press Ctr-C to interrupt it!")
        open = requests.get(url=URL_OPEN)
        print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
        print("Vehicle bridge status: " + ("open" if open.json() else "closed"))
        print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    except:
        print("‼️ Fail ‼️ - Connection to service failed! Making another attempt in 10 seconds")
    sleep(10)
