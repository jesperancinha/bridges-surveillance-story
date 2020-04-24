from multiprocessing import Process
from time import sleep

import requests

from lauch_start_train import start_train
from lauch_start_vehicle import start_vehicle


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
URL_BRIDGE_AREA = "http://localhost:9000/api/bridge/logistics/schedules/area/52.347293/4.912372"

while True:
    print("Simulation ongoing. Press Ctr-C to interrupt it!")
    open = requests.get(url=URL_OPEN)
    area = requests.get(url=URL_BRIDGE_AREA)
    print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    print("Bridge Open: " + str(open.json()))
    print("You are in the bridge area: " + str(area.json()))
    print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    sleep(1)
