# -*- coding: utf-8 -*-
from multiprocessing import Process
from time import sleep

import requests

from launch_start_train import start_train
from launch_start_vehicle import start_vehicle


def train_simulation():
    while True:
        start_train('localhost')


def vehicle_simulation():
    while True:
        start_vehicle('localhost')


train_simulation_process = Process(target=train_simulation)
vehicle_simulation_process = Process(target=vehicle_simulation)

train_simulation_process.start()
vehicle_simulation_process.start()

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
