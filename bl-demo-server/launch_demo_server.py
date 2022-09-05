# -*- coding: utf-8 -*-
import ctypes
import multiprocessing
import os
import sys
from multiprocessing import Process
from time import sleep

import requests
from distlib.compat import raw_input

sys.path.insert(1, os.path.abspath('bl-core-service'))
sys.path.insert(2, os.path.abspath('../bl-core-service'))
sys.path.insert(3, os.path.abspath('bl-demo-server/bl-core-service'))

if os.path.exists("bl-demo-server"):
    os.chdir("bl-demo-server")

from launch_start_train import start_train
from launch_start_bridge_meters import start_bridge_meters


def train_simulation(train_result_simulation):
    value = start_train('localhost')
    train_result_simulation.value = value


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
    bridge_meters_simulation_process = Process(target=bridge_meter_simulation)
    bridge_meters_simulation_process_killer = Process(target=bridge_meter_simulation_killer,
                                                      args=[bridge_meters_simulation_process])

    URL_OPEN = "http://localhost:9000/api/bridge/logistics/schedules/open/52.347293/4.912372"

    simulation_ready = False
    while not simulation_ready:
        try:
            open = requests.get(url=URL_OPEN, timeout=5)
            simulation_ready = True
        except:
            print("🔴 Simulation not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            print("ℹ️ Make sure you wait enough time for docker-compose to start.")
            print("ℹ️ Check file Checkups.md for troubleshooting purposes.")
            print("ℹ️ If the demo doesn't start, please open an issue for me in the Gitlab repo: "
                  "https://gitlab.com/jesperancinha/bridge-logistics/-/issues")
            sleep(10)

    train_simulation_process.start()
    bridge_meters_simulation_process.start()
    bridge_meters_simulation_process_killer.start()

    simulation_ready = False

    while train_simulation_process.is_alive() or bridge_meters_simulation_process.is_alive():
        # while train_simulation_process.is_alive():
        try:
            print(("🟢" if simulation_ready else "🟠") + " Simulation ongoing. Press Ctr-C to interrupt it!")
            open = requests.get(url=URL_OPEN, timeout=5)
            print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
            print(("🟢" if train_simulation_process.is_alive() else "🔴") + "Train simulation")
            print(("🟢" if bridge_meters_simulation_process.is_alive() else "🔴") + "Bridge simulation")
            print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
            simulation_ready = True
        except:
            simulation_ready = False
            print("🔴 Bridge Scheduling Service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
        sleep(10)

    print("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")
    print(("🟢" if train_simulation_process.is_alive() else "🔴") + "Train simulation")
    print(("🟢" if bridge_meters_simulation_process.is_alive() else "🔴") + "Bridge simulation")
    print("-=-=-=-=-=-=-=-=-=- =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-")

    print("🚂 🚚 🌉 Simulation complete!")

    train_result_value = train_result.value
    # Unblock this if you want to cheat 😉
    # print(train_result_value)

    secret_spy = train_result_value["secret_spy"]
    special_agent = train_result_value["special_agent"]
    suspects = train_result_value["suspects"]
    suspects_names = map(lambda y: y["firstName"] + " " + y["lastName"], suspects)
    secret_spy_name = secret_spy["firstName"] + " " + secret_spy["lastName"]
    special_agent_name = special_agent["firstName"] + " " + special_agent["lastName"]
    print("Chapter I - The parachute escapee")
    print("After the train arrived to the station, one passenger didn't check out.")
    print("The police got alerted because they saw what it looked like a person that had jumped out of a moving train "
          "on a parachute.")
    print("They also got an anonymous tip that a special agent is in the train. That special agent needs to stay in "
          "cover.")
    print("Unfortunately, for the special agent, extremely sensitive information has been stolen.")
    print("Furthermore, it is also announced that the train station will shut down all travelling through the bridge")

    x = raw_input("Who do you think stole the suitcase? Remember that the information present in the suitcase has "
                  "extremely sensitive information.")

    print("We have these suspects: " + str(suspects_names))
    if x in suspects_names:
        if len(suspects) > 1:
            print("You got the suspect right! 🎊 Unfortunately more investigation need to be done. These were all the "
                  "suspects:" + str(suspects_names))
            print("After a few weeks of investigation, the police finally found : " + secret_spy_name)
        else:
            print("Correct!")
        print("You have been successful in finding the secret spy! 🕵️")
        print(secret_spy_name + " unfortunately didn't obey police 🚓 instructions and didn't surrender to the police.")
        print(secret_spy_name + " decided to try to escape and after a long run the police finally caught up.")
    else:
        print("Wrong!")
        print("You have failed in finding the secret spy!")
        print(secret_spy_name + " is now free to cause damage to the world. The sensitive information lead to an "
                                "escalation in tensions between countries and ultimately a new world war. 🔥")
        print("The world is now in danger!")
