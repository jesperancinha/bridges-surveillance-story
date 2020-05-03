# -*- coding: utf-8 -*-
import json
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

sys.path.insert(1, os.path.abspath('../bl-vehicle-services'))
sys.path.insert(2, os.path.abspath('bl-vehicle-services'))
sys.path.insert(3, os.path.abspath('../bl-bridge-services'))
sys.path.insert(4, os.path.abspath('bl-bridge-services'))
sys.path.insert(5, os.path.abspath('bl-demo-server/bl-vehicle-services'))
sys.path.insert(6, os.path.abspath('bl-demo-server/bl-bridge-services'))

from send_vehicle_timestamp import send_signal as send_vehicle_signal
from send_bridge_timestamp import send_signal as send_bridge_signal
from send_vehicle_merchandise import send_merchandise as send_vehicle_merchandise

URL_BRIDGE_AREA = "http://localhost:9000/api/bridge/logistics/schedules/area/"
URL_OPEN = "http://localhost:9000/api/bridge/logistics/schedules/open/"

dest_lat = 52.347293
dest_lon = 4.912372


def current_time():
    return int(round(time.time() * 1000))


def get_vehicle_checkin_data(coord):
    return {
        'id': 1,
        'source': 'VEHICLE',
        'type': 'CHECKIN',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_vehicle_checkout_data(coord):
    return {
        'id': 1,
        'source': 'VEHICLE',
        'type': 'CHECKOUT',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkin_data(coord):
    return {
        'id': 1,
        'source': 'BRIDGE',
        'type': 'CHECKIN',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkout_data(coord):
    return {
        'id': 1,
        'source': 'BRIDGE',
        'type': 'CHECKOUT',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station, origin, d_lat, d_lon, d_lat2, d_lon2):
    print("🚚 🛣 Vehicle is underway. Just left the supplier central 🏪")
    vehicle_message_process_to_bridge = Process(target=pulses, args=[host, origin, d_lat, d_lon])
    vehicle_message_process_to_bridge.start()
    sleep(time_to_get_to_bridge)
    success = False
    while not success:
        try:
            area = requests.get(url=URL_OPEN + str(dest_lat) + "/" + str(dest_lon), timeout=5)
            while not area.json():
                area = requests.get(url=URL_OPEN + str(dest_lat) + "/" + str(dest_lon), timeout=5)
                print("⛔️ 🌉 Bridge is closed!")
                sleep(1)
            print("✅ 🌉 Bridge is open!")
            print("🚚 Entering Bridge...")
            send_checkin_message(host, origin)
            print("🚚 🔶 Checked In!")
            sleep(5)
            vehicle_message_process_to_bridge.terminate()
            send_checkout_message(host, origin)
            print("🚚 🟩 Checked Out!")
            print("🚚 🛣 Leaving Bridge...")
            vehicle_message_process_to_station = Process(target=pulses, args=[host, origin, d_lat2, d_lon2])
            vehicle_message_process_to_station.start()
            sleep(time_to_get_to_station)
            vehicle_message_process_to_station.terminate()
            success = True
        except:
            print("🔴 Bridge Scheduling Service not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)


def pulses(host, origin, d_lat, d_lon):
    success = False
    while not success:
        try:
            send_merchandise_message(host, origin, 'LOADED')
            success = True
        except:
            print("🔴 Vehicle Merchandise queue not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)
    while True:
        sleep(1)
        origin.delta(d_lat, d_lon)
        send_merchandise_message(host, origin, 'INTRANSIT')


def send_merchandise_message(host, origin, status):
    area = requests.get(url=URL_BRIDGE_AREA + str(origin.lat) + "/" + str(origin.lon))
    with open('../bl-simulation-data/freight.json') as json_file:
        data = json.load(json_file)
        data[0].update({'status': status})
        send_vehicle_merchandise(host, data)
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
    send_merchandise_message(host, origin, 'DELIVERED')
