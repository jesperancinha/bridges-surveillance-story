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
    criminal = train_result_value["criminal"]
    suspects = train_result_value["suspects"]
    suspects_names = map(lambda y: y["firstName"] + " " + y["lastName"], suspects)
    victims_name = victim["firstName"] + " " + victim["lastName"]
    criminal_name = criminal["firstName"] + " " + criminal["lastName"]
    print("Chapter I - The Murder")
    print("After the train arrived to the station, one passenger didn't check out.")
    print("The police makes a public announcement that a body has been found floating in the river.")
    print("In the statement, police say that the body hasn't been recognized yet and that any information related to the finding should be reported immediately")
    print("Furthermore, it is also announced that the train station will shut down all travelling through the bridge")
    print("A few days later, the police finally identifies the body and contacts the victim's mother, miss " + mother_of_the_victim)
    print("Detective - Miss " + mother_of_the_victim + " I have to ask you one more time. Are you sure you want to talk now?")
    print(mother_of_the_victim + " - Yes. I'm sure! My child was the best thing in this world. A perfect soul. I don't understand what happened, but I want to find their killer.")
    print("Detective - Their?")
    print(mother_of_the_victim + " - My child was different. They didn't want to be called a boy nor they wanted to be called a girl. They called self a '" + victim["gender"] + "'")
    print("Detective - That is very interesting madam. You child's name was " + victims_name + ". Is that correct?")
    print(mother_of_the_victim + " - Yes, that is correct.")
    print("Detective - Did he... sorry, did they had enemies?")
    print(mother_of_the_victim + " - They did had enemies, Detective. They got multiple time issues at work because they was so different. They got fired a couple of times because of it. Still they fought so much...")
    print("Detective - I understand. For me it's just important to get enough important data to track down the killer. Can you name someone?")
    print(mother_of_the_victim + " - I have no idea, they never told me anything about work. Only that people took advantage of their sympathy and ingenuity. Their heart was just bigger than that.")
    print("Detective - Hold on madam, I have a phone call. Hello? Really?!?!? Are you sure you got the right one?")

    x = raw_input("Who do you think committed the murder? ")

    print("We have these suspects: " + str(suspects_names))
    if x in suspects_names:
        if len(suspects) > 1:
            print("You got the suspect right! Unfortunately more investigation need to be done. These were all the suspects:" + str(suspects_names))
            print("After a few weeks of investigation, the police finally found the murderer: " + criminal_name)
        else:
            print("Correct!")
        print("You have been successful in finding the murderer!")
        print(criminal_name + " unfortunately didn't obey police instructions and instead of turning " +
              ("himself " if criminal["gender"] == "Cis Man" else "herself ") + "in, " +
              ("he " if criminal["gender"] == "Cis Man" else "she ") + "decided to try to escape, and the police had to follow the standard procedure bringing " +
              ("his " if criminal["gender"] == "Cis Man" else "her ") + "life to a very sad end.")
    else:
        print("Wrong!")
        print("You have failed in finding the murderer!")
        print(criminal_name + " is now free to cause damage to the world." +
              ("He " if criminal["gender"] == "Cis Man" else "She ") +
              "will be successful in creating an inhospitable society where being different is seen as a disease")
        print("The free world has been destroyed!")
