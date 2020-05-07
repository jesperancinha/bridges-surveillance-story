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

    mother_of_the_victim = train_result_value["mother_victim"]
    victim = train_result_value["victim"]
    suspects = train_result_value["suspects"]
    print("After the train arrived to the station, one passenger didn't check out.")
    print("The police makes a public announcement that a body has been found on the bridge.")
    print("In the statement, police say that the body parts are spread between the bridge and the river and that the full body has not been recovered yet")
    print("Furthermore, it is also announced that the train station will shut down all travelling through the bridge")
    print("A few days later, the police contacts the victims mother, miss " + mother_of_the_victim)
    print("Detective - Miss " + mother_of_the_victim + " I have to ask you one more time. Are you sure you want to talk now?")
    print(mother_of_the_victim + " - Yes. I'm sure! My child was the best thing in this world. A perfect soul. I don't understand what happened, but I want to find their killer.")
    print("Detective - Their?")
    print(mother_of_the_victim + " - My child was different. He didn't want to be called a boy nor he wanted to be called a girl. He called himself a '" + victim["gender"] + "'")
    print("Detective - That is very interesting madam. Did he... sorry, did they had enemies?")
    print(mother_of_the_victim + " - They did had enemies, Detective. He got multiple time issues at work because he was so different. He got fired a couple of times because of it. Still he fought so much...")
    print("Detective - I understand. For me it's just important to get enough important dat to track down the killer. Can you name someone?")
    print(mother_of_the_victim + " - I have no idea, they never told me anything about work. Only that people took advantage of their sympathy and ingenuity. Their heart was just bigger than that.")
    print("Detective - Hold on madam, I have a phone call. Hello? Really?!?!? Are you sure you got the right one?")

    print("Who do you think committed the murder?")
    x = input()
    if x in suspects:
        if len(suspects) > 1:
            print("Unfortunately more investigation needs to be done. These are the suspects:" + str(suspects))
        else:
            print("Correct!")
    else:
        print("Fail!")
