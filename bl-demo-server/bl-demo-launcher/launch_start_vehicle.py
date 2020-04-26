# -*- coding: utf-8 -*-
import os
import random
import sys
import time
from multiprocessing import Process
from time import sleep

import requests

# sys.path.append(os.path.abspath('../../bl-vehicle-server/bl-vehicle-sensor-service'))
# sys.path.append(os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))
from geo_calculator import Coord, create_west_random_point, create_east_random_point

sys.path.insert(1, os.path.abspath('../../bl-vehicle-server/bl-vehicle-sensor-service'))
sys.path.insert(2, os.path.abspath('bl-vehicle-server/bl-vehicle-sensor-service'))
sys.path.insert(3, os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))
sys.path.insert(4, os.path.abspath('bl-bridge-server/bl-bridge-sensor-service'))

from send_vehicle_timestamp import send_signal as send_vehicle_signal
from send_bridge_timestamp import send_signal as send_bridge_signal

URL_BRIDGE_AREA = "http://localhost:9000/api/bridge/logistics/schedules/area/"
URL_OPEN = "http://localhost:9000/api/bridge/logistics/schedules/open/"

dest_lat = 52.347293
dest_lon = 4.912372

def current_time():
    return int(round(time.time() * 1000))


def get_vehicle_checkin_data(coord):
    return {
        'id': 1,
        'source': 'vehicle',
        'type': 'checkin',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_vehicle_checkout_data(coord):
    return {
        'id': 1,
        'source': 'vehicle',
        'type': 'checkout',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkin_data(coord):
    return {
        'source': 'bridge',
        'type': 'checkin',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkout_data(coord):
    return {
        'source': 'bridge',
        'type': 'checkout',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station, origin, d_lat, d_lon, d_lat2, d_lon2):
    print("🚚 🛣 Vehicle is underway. Just left the supplier central 🏪")
    vehicle_message_process_to_bridge = Process(target=pulses, args=[origin, d_lat, d_lon])
    vehicle_message_process_to_bridge.start()
    sleep(time_to_get_to_bridge)
    area = requests.get(url=URL_OPEN + str(dest_lat) + "/" + str(dest_lon))
    while not area.json():
        area = requests.get(url=URL_OPEN + str(dest_lat) + "/" + str(dest_lon))
        print(area.json())
        print("⛔️ 🌉 Bridge is closed!")
        sleep(1)
    print("✅ 🌉 Bridge is open!")
    print("Entering Bridge...")
    send_checkin_message(host, origin)
    print("🚚 🔶 Checked In!")
    sleep(5)
    vehicle_message_process_to_bridge.terminate()
    send_checkout_message(host, origin)
    print("🚚 🟩 Checked Out!")
    print("🚚 🛣 Leaving Bridge...")
    vehicle_message_process_to_station = Process(target=pulses, args=[origin, d_lat2, d_lon2])
    vehicle_message_process_to_station.start()
    sleep(time_to_get_to_station)
    vehicle_message_process_to_station.terminate()


def pulses(origin, d_lat, d_lon):
    while True:
        sleep(1)
        origin.delta(d_lat, d_lon)
        send_merchandise_message(origin)


def send_merchandise_message(origin):
    area = requests.get(url=URL_BRIDGE_AREA + str(origin.lat) + "/" + str(origin.lon))
    print("🚚 Vehicle Merchandise sent! " + str(current_time()))
    print("🚚 Vehicle location: " + str(origin))
    print("🚚 Vehicle in the 🌉 bridge area" if area.json() else "🚚 Vehicle not in the bridge area")


def send_checkin_message(host, coord):
    send_vehicle_signal(host, get_vehicle_checkin_data(coord))
    send_bridge_signal(host, get_bridge_checkin_data(coord))
    print("Check In sent!")


def send_checkout_message(host, coord):
    send_vehicle_signal(host, get_vehicle_checkout_data(coord))
    send_bridge_signal(host, get_bridge_checkout_data(coord))
    print("Check Out sent!")


def start_vehicle(host):
    # For our simulation we are using a converted simulation from minutes to hours.
    # This means that whereas in the real case we would find that the vehicle would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
    # vehicle simulation from time import sleep

    time_to_get_to_bridge = random.randint(18, 20)
    time_to_get_to_station = random.randint(18, 20)

    dest = Coord(dest_lat, dest_lon)
    origin = create_west_random_point(dest, 100)
    station = create_east_random_point(dest, 100)

    d_lat = (dest.lat - origin.lat) / time_to_get_to_bridge
    d_lat2 = (station.lat - station.lat) / time_to_get_to_station
    d_lon = (dest.lon - origin.lon) / time_to_get_to_bridge
    d_lon2 = (station.lon - station.lon) / time_to_get_to_station
    print(dest)
    print(origin)
    print(station)

    vehicle_checkin_checkout_process = Process(target=check_in_out, args=[host, time_to_get_to_bridge, time_to_get_to_station,
                                                                          origin, d_lat, d_lon, d_lat2, d_lon2])

    print("Time to get to bridge - " + str(time_to_get_to_bridge))
    print("Time to get back to central - " + str(time_to_get_to_station))

    vehicle_checkin_checkout_process.start()
    vehicle_checkin_checkout_process.join()
    vehicle_checkin_checkout_process.terminate()

    print("🚚 Arrived at the supplier central! 🏪")
