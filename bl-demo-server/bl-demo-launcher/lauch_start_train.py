import os
import random
import sys
import time
from multiprocessing import Process
from time import sleep

# sys.path.append(os.path.abspath('../../bl-train-server/bl-train-sensor-service'))
# sys.path.append(os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))

sys.path.insert(1, os.path.abspath('../../bl-train-server/bl-train-sensor-service'))
sys.path.insert(2, os.path.abspath('bl-train-server/bl-train-sensor-service'))
sys.path.insert(3, os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))
sys.path.insert(4, os.path.abspath('bl-bridge-server/bl-bridge-sensor-service'))

from send_train_timestamp import send_signal as send_train_signal
from send_bridge_timestamp import send_signal  as send_bridge_signal


def current_time():
    return int(round(time.time() * 1000))


def get_train_checkin_data():
    return {
        'id': 1,
        'source': 'train',
        'type': 'checkin',
        'timestamp': current_time(),
        'lat': 52.109788,
        'lon': 5.077982
    }


def get_train_checkout_data():
    return {
        'id': 1,
        'source': 'train',
        'type': 'checkout',
        'timestamp': current_time(),
        'lat': 52.110822,
        'lon': 5.076083
    }


def get_bridge_checkin_data():
    return {
        'source': 'bridge',
        'type': 'checkin',
        'timestamp': current_time(),
        'lat': 52.109788,
        'lon': 5.077982
    }


def get_bridge_checkout_data():
    return {
        'source': 'bridge',
        'type': 'checkout',
        'timestamp': current_time(),
        'lat': 52.110822,
        'lon': 5.076083
    }


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station):
    sleep(time_to_get_to_bridge)
    print("Entering Bridge...")
    send_checkin_message(host)
    print("Checked In!")
    sleep(5)
    send_checkout_message(host)
    print("Checked Out!")
    print("Leaving Bridge...")
    sleep(time_to_get_to_station)


def pulses():
    while True:
        sleep(1)
        send_merchandise_message()


def send_merchandise_message():
    print("Train Merchandise sent! " + str(current_time()))


def send_checkin_message(host):
    send_train_signal(host, get_train_checkin_data())
    send_bridge_signal(host, get_bridge_checkin_data())
    print("Check In sent!")


def send_checkout_message(host):
    send_train_signal(host, get_train_checkout_data())
    send_bridge_signal(host, get_bridge_checkout_data())
    print("Check Out sent!")


def start_train(host):
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
