import os
import random
import sys
import time
from multiprocessing import Process
from time import sleep

sys.path.append(os.path.abspath('../../bl-train-server/bl-train-merchandise-service'))
sys.path.insert(1, os.path.abspath('../../bl-train-server/bl-train-merchandise-service'))

from send_train_timestamp import sendSignal

milliseconds = int(round(time.time() * 1000))

checkInData = {
    'id': 1,
    'type': 'train',
    'checkin': milliseconds,
    'lat': 52.109788,
    'lon': 5.077982
}

checkOutData = {
    'id': 1,
    'type': 'train',
    'checkout': milliseconds,
    'lat': 52.110822,
    'lon': 5.076083
}


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station):
    sleep(time_to_get_to_bridge)
    print("Entering Bridge...")
    send_checkin_message(host, checkInData)
    print("Checked In!")
    sleep(5)
    send_checkout_message(host, checkInData)
    print("Checked Out!")
    print("Leaving Bridge...")
    sleep(time_to_get_to_station)



def pulses():
    while True:
        sleep(1)
        send_merchandise_message()


def generate_checkin():
    send_merchandise_message()
    print("Checked in!")


def send_merchandise_message():
    print("Merchansise sent!")


def send_checkin_message(host, checkInData):
    sendSignal(host, checkInData)
    print("Check In sent!")


def send_checkout_message(host, checkOutData):
    sendSignal(host, checkOutData)
    print("Check Out sent!")


def startTrain(host):
    # For our simulation we are using a converted simulation from minutes to hours.
    # This means that whereas in the real case we would find that the train would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
    # Train simulation from time import sleep

    time_to_get_to_bridge = random.randint(8, 10)
    time_to_get_to_station = random.randint(8, 10)

    train_checkin_checkout_process = Process(target=check_in_out, args=[host, time_to_get_to_bridge, time_to_get_to_station])
    train_message_process = Process(target=pulses, args=())

    print("Time to get to bridge - " + str(time_to_get_to_bridge))
    print("Time to get back to station - " + str(time_to_get_to_station))

    train_checkin_checkout_process.start()
    train_message_process.start()

    train_checkin_checkout_process.join()
    train_checkin_checkout_process.terminate()
    train_message_process.terminate()

    print("Arrived at the station!")
