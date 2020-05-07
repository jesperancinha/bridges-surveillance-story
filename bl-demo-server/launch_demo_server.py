# -*- coding: utf-8 -*-
import ctypes
import multiprocessing
import os
import sys
from multiprocessing import Process
from time import sleep

import requests

sys.path.insert(1, os.path.abspath('bl-core-service'))
sys.path.insert(2, os.path.abspath('../bl-core-service'))
sys.path.insert(3, os.path.abspath('bl-demo-server/bl-core-service'))

if os.path.exists("bl-demo-server"):
    os.chdir("bl-demo-server")

from launch_start_train import start_train
from launch_start_vehicle import start_vehicle
from launch_start_bridge_meters import start_bridge_meters


def train_simulation(train_result_simulation):
    value = start_train('localhost')
    train_result_simulation.value = value


def vehicle_simulation():
    start_vehicle('localhost')


def bridge_meter_simulation():
    while True:
        start_bridge_meters('127.0.0.1')


def bridge_meter_simulation_killer(bridge_meter_simulation_process):
    sleep(30)
    bridge_meter_simulation_process.terminate()


if __name__ == '__main__':
    manager = multiprocessing.Manager()
    train_result = manager.Value(ctypes.c_char_p, "")
    train_simulation_process = Process(target=train_simulation, args=[train_result])
    vehicle_simulation_process = Process(target=vehicle_simulation)
    bridge_meters_simulation_process = Process(target=bridge_meter_simulation)
    bridge_meters_simulation_process_killer = Process(target=bridge_meter_simulation_killer, args=[bridge_meters_simulation_process])

    URL_OPEN = "http://localhost:9000/api/bridge/logistics/schedules/open/52.347293/4.912372"

    simulation_ready = False
    while not simulation_ready:
        try:
            open = requests.get(url=URL_OPEN, timeout=5)
            simulation_ready = True
        except:
            print("🔴 Simulation not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)

    train_simulation_process.start()
    vehicle_simulation_process.start()
    bridge_meters_simulation_process.start()
    bridge_meters_simulation_process_killer.start()

    simulation_ready = False

    while train_simulation_process.is_alive() or vehicle_simulation_process.is_alive() or bridge_meters_simulation_process.is_alive():
        # while train_simulation_process.is_alive():
        try:
            print(("🟢" if simulation_ready else "🟠") + " Simulation ongoing. Press Ctr-C to interrupt it!")
            open = requests.get(url=URL_OPEN, timeout=5)
            print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
            print("🌉 Vehicle bridge status: " + ("🟢 open" if open.json() else "🔴 closed"))
            print(("🟢" if train_simulation_process.is_alive() else "🔴") + "Train simulation")
            print(("🟢" if vehicle_simulation_process.is_alive() else "🔴") + "Vehicle simulation")
            print(("🟢" if bridge_meters_simulation_process.is_alive() else "🔴") + "Bridge simulation")
            print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
            simulation_ready = True
        except:
            simulation_ready = False
            print("🔴 Bridge Scheduling Service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
        sleep(10)

    print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    print(("🟢" if train_simulation_process.is_alive() else "🔴") + "Train simulation")
    print(("🟢" if vehicle_simulation_process.is_alive() else "🔴") + "Vehicle simulation")
    print(("🟢" if bridge_meters_simulation_process.is_alive() else "🔴") + "Bridge simulation")
    print("-=-=-=-=-=-=-=-=-=- =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")

    print("🚂 🚚 🌉 Simulation complete!")

    train_result_value = train_result.value
    # Unblock this if you want to cheat 😉
    # print(train_result_value)

    print("After the train arrived to the station, one passenger didn't check out.")
    print("The police makes a public announcement that a body has been found on the bridge.")
    print("In the statement, police say that the body parts are spread between the bridge and the river and that the full body has not been recovered yet")
    print("Furthermore, it is also announced that the train station will shut down all travelling through the bridge")
    print("A few days later, the police contacts the victims mother, miss " + train_result_value["mother_victim"])
