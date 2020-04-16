import random
from multiprocessing import Process
from time import sleep


def pulses():
    while True:
        sleep(1)
        print("Message Sent!")


def generate_merchandise():
    print("Merchandise generated!")


# For our simulation we are using a converted simulation from minutes to hours.
# This means that whereas in the real case we would find that the train would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
# Train simulation from time import sleep

timeToGetToBridge = random.randint(80, 120)
generate_merchandise()

print("Time to get to bridge - " + str(timeToGetToBridge))
p = Process(target=pulses, args=())
p.start()

sleep(1)
p.terminate()

print("Success!")
