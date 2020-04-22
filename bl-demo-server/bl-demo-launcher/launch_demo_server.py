from multiprocessing import Process
from time import sleep

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

while True:
    print("Simulation ongoing. Press Ctr-C to interrupt it!")
    sleep(10)
