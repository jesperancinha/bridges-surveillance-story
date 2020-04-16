import os
import random
import sys
from multiprocessing import Process
from time import sleep

sys.path.append(os.path.abspath("../../bl-train-server/bl-train-merchandise-service/send_merchandise.py"))


def check_in_out():
    send_checkin_message()
    print("Checked In!")
    sleep(5)
    send_checkout_message()
    print("Checked Out!")


def pulses():
    while True:
        sleep(1)
        send_merchandise_message()

def generate_checkin():
    send_merchandise_message()
    print("Checked in!")


def send_merchandise_message():
    print("Merchansise sent!")


def send_checkin_message():
    print("Check In sent!")


def send_checkout_message():
    print("Check Out sent!")


# For our simulation we are using a converted simulation from minutes to hours.
# This means that whereas in the real case we would find that the train would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
# Train simulation from time import sleep

timeToGetToBridge = random.randint(80, 120)

train_checker_process = Process(target=check_in_out, args=())
train_message_process = Process(target=pulses, args=())

print("Time to get to bridge - " + str(timeToGetToBridge))
print("Entering Bridge...")

train_checker_process.start()
train_message_process.start()

train_checker_process.join()

print("Leaving Bridge...")

train_checker_process.terminate()
train_message_process.terminate()

print("Success!")
