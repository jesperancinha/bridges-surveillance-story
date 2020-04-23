import os
import random
import sys
import time
from multiprocessing import Process
from time import sleep

# sys.path.append(os.path.abspath('../../bl-vehicle-server/bl-vehicle-sensor-service'))
# sys.path.append(os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))

sys.path.insert(1, os.path.abspath('../../bl-vehicle-server/bl-vehicle-sensor-service'))
sys.path.insert(2, os.path.abspath('bl-vehicle-server/bl-vehicle-sensor-service'))
sys.path.insert(3, os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))
sys.path.insert(4, os.path.abspath('bl-bridge-server/bl-bridge-sensor-service'))

from send_vehicle_timestamp import send_signal as send_vehicle_signal
from send_bridge_timestamp import send_signal as send_bridge_signal


def current_time():
    return int(round(time.time() * 1000))


def get_vehicle_checkin_data():
    return {
        'id': 1,
        'source': 'vehicle',
        'type': 'checkin',
        'timestamp': current_time(),
        'lat': 52.109788,
        'lon': 5.077982
    }


def get_vehicle_checkout_data():
    return {
        'id': 1,
        'source': 'vehicle',
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
    print("Vehicle Merchandise sent! " + str(current_time()))


def send_checkin_message(host):
    send_vehicle_signal(host, get_vehicle_checkin_data())
    send_bridge_signal(host, get_bridge_checkin_data())
    print("Check In sent!")


def send_checkout_message(host):
    send_vehicle_signal(host, get_vehicle_checkout_data())
    send_bridge_signal(host, get_bridge_checkout_data())
    print("Check Out sent!")


def start_vehicle(host):
    # For our simulation we are using a converted simulation from minutes to hours.
    # This means that whereas in the real case we would find that the vehicle would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
    # vehicle simulation from time import sleep

    time_to_get_to_bridge = random.randint(8, 10)
    time_to_get_to_station = random.randint(8, 10)

    vehicle_checkin_checkout_process = Process(target=check_in_out, args=[host, time_to_get_to_bridge, time_to_get_to_station])
    vehicle_message_process = Process(target=pulses, args=())

    print("Time to get to bridge - " + str(time_to_get_to_bridge))
    print("Time to get back to central - " + str(time_to_get_to_station))

    vehicle_checkin_checkout_process.start()
    vehicle_message_process.start()

    vehicle_checkin_checkout_process.join()
    vehicle_checkin_checkout_process.terminate()
    vehicle_message_process.terminate()

    print("Arrived at the central!")
