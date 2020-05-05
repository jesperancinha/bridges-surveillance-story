# -*- coding: utf-8 -*-
import functools
import json
import os
import random
import sys
import time
from multiprocessing import Process
from time import sleep

# sys.path.append(os.path.abspath('../../bl-train-server/bl-train-sensor-service'))
# sys.path.append(os.path.abspath('../../bl-bridge-server/bl-bridge-sensor-service'))
from geo_calculator import Coord, create_west_random_point, create_east_random_point

sys.path.insert(1, os.path.abspath('../bl-train-services'))
sys.path.insert(2, os.path.abspath('bl-train-services'))
sys.path.insert(3, os.path.abspath('../bl-bridge-services'))
sys.path.insert(4, os.path.abspath('bl-bridge-services'))
sys.path.insert(5, os.path.abspath('bl-demo-server/bl-train-services'))
sys.path.insert(6, os.path.abspath('bl-demo-server/bl-bridge-services'))

from send_train_timestamp import send_signal as send_train_signal
from send_bridge_timestamp import send_signal  as send_bridge_signal
from send_merchandise import send_merchandise as send_train_merchandise
from launch_generate_people import generate_all_passengers
from send_people_readings import send_people


def current_time():
    return int(round(time.time() * 1000))


def get_train_checkin_data(coord):
    return {
        'id': 1,
        'source': 'TRAIN',
        'type': 'CHECKIN',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_train_checkout_data(coord):
    return {
        'id': 1,
        'source': 'TRAIN',
        'type': 'CHECKOUT',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkin_data(coord):
    return {
        'id': 2,
        'source': 'BRIDGE',
        'type': 'CHECKIN',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def get_bridge_checkout_data(coord):
    return {
        'id': 2,
        'source': 'BRIDGE',
        'type': 'CHECKOUT',
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station, origin, d_lat, d_lon, d_lat2, d_lon2, passengers):
    print("🚂 🛤 Train is underway. Just left central statin 🏫")
    success = False
    while (not success):
        try:
            send_merchandise_message(host, origin, 'LOADED')
            success = True
        except:
            print("🔴 Train Merchandise queue not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)
    train_message_process = Process(target=pulses, args=[host, origin, d_lat, d_lon, passengers])
    train_message_process.start()
    sleep(time_to_get_to_bridge)
    print("🚂 🌉 Train entering Bridge...")
    send_checkin_message(host, origin)
    print("🚂 Train Checked In!")
    sleep(5)
    train_message_process.terminate()
    send_checkout_message(host, origin)
    print("🚂 Train Checked Out!")
    print("🚂 Train Leaving Bridge...")
    train_message_process = Process(target=pulses, args=[host, origin, d_lat2, d_lon2, passengers])
    train_message_process.start()
    sleep(time_to_get_to_station)
    train_message_process.terminate()


def pulses(host, origin, d_lat, d_lon, passengers):
    while True:
        sleep(1)
        origin.delta(d_lat, d_lon)
        send_merchandise_message(host, origin, 'INTRANSIT')
        send_passenger_messages(host, origin, 'INTRANSIT', passengers)


def send_passenger_messages(host, origin, param, passengers):
    for passenger in passengers:
        passenger.update({'lat': origin.lat})
        passenger.update({'lon': origin.lon})
    send_people(host, passengers)


def send_merchandise_message(host, origin, status):
    with open('../bl-simulation-data/train.json') as json_file:
        data = json.load(json_file)
        data[0].update({'status': status})
        data[0].update({'lat': origin.lat})
        data[0].update({'lon': origin.lon})
        success = False
        while not success:
            try:
                send_train_merchandise(host, data)
                success = True
            except:
                print("🔴 Train Merchandise queue error. Press Ctr-C to stop. Retry in 10 seconds...")
                sleep(10)

        print("🚂 Train Merchandise sent! " + str(current_time()))
        print("🚂 Train location: " + str(origin))


def send_checkin_message(host, origin):
    send_train_signal(host, get_train_checkin_data(origin))
    send_bridge_signal(host, get_bridge_checkin_data(origin))
    print("Train Check In sent!")


def send_checkout_message(host, origin):
    send_train_signal(host, get_train_checkout_data(origin))
    send_bridge_signal(host, get_bridge_checkout_data(origin))
    print("Train Check Out sent!")


def start_train(host):
    # For our simulation we are using a converted simulation from minutes to hours.
    # This means that whereas in the real case we would find that the train would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
    # Train simulation from time import sleep

    time_to_get_to_bridge = random.randint(18, 20)
    time_to_get_to_station = random.randint(18, 20)

    dest_lat = 52.110822
    dest_lon = 5.076083678
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

    passengers = generate_all_passengers(300)

    for passenger in passengers:
        passenger.update({"unit": "kg"})
        passenger.update({"weight": random.randint(40, 120)})

    total_passenger_weight = functools.reduce(lambda a, b:
                                              a + int(b["weight"])
                                              , passengers, 0)

    with open('../bl-simulation-data/carriages.json') as carriages_json:
        with open('../bl-simulation-data/train.json') as trains_json:
            data = json.load(trains_json)
            carriages = data[0]["composition"]
            no_package_carriages = filter(lambda x: not "packageId" in x, carriages)
            json_carriages = json.load(carriages_json)
            train_carriages = map(lambda y:
                                  filter(lambda z:
                                         z["id"] == y["carriageId"],
                                         json_carriages)[0], no_package_carriages)
            just_people_train_carriages = list(filter(lambda x: x["type"] == "people", train_carriages))
            no_toilet_carriages = list(filter(lambda x: x["toilet"] == False, just_people_train_carriages))
            cis_passengers = filter(lambda x: x["gender"] == "Cis Man" or x["gender"] == "Cis Woman", passengers)
            no_toilet_carriages_capacity = functools.reduce(lambda a, b:
                                                            a + int(b["passengers"])
                                                            , no_toilet_carriages, 0)
            storyline_cis_max_cap = no_toilet_carriages_capacity / 2
            no_toilet_carriages_number = len(no_toilet_carriages)
            current_carriage_index = 0
            cis_passengers_cap = cis_passengers[0: storyline_cis_max_cap]
            for passenger in cis_passengers_cap:
                current_carriage = no_toilet_carriages[current_carriage_index]
                passenger.update({"carriageId": current_carriage["id"]})
                current_carriage_index += 1
                if current_carriage_index >= no_toilet_carriages_number:
                    current_carriage_index = 0

            total_carriages_number = len(just_people_train_carriages)
            current_carriage_index = 0
            for passenger in passengers:
                while not "carriageId" in passenger:
                    current_carriage = just_people_train_carriages[current_carriage_index]
                    passenger.update({"carriageId": current_carriage["id"]})
                    current_carriage_index += 1
                    if current_carriage_index >= total_carriages_number:
                        current_carriage_index = 0

        print(passengers)

    print("Passengers total weight is " + str(total_passenger_weight))

    train_checkin_checkout_process = Process(target=check_in_out, args=[host, time_to_get_to_bridge, time_to_get_to_station,
                                                                        origin, d_lat, d_lon, d_lat2, d_lon2, passengers])

    print("Time to get to bridge - " + str(time_to_get_to_bridge))
    print("Time to get back to station - " + str(time_to_get_to_station))

    train_checkin_checkout_process.start()
    train_checkin_checkout_process.join()
    train_checkin_checkout_process.terminate()

    print("🚂 Arrived at the train central station! 🏫")
    send_merchandise_message(host, origin, 'DELIVERED')
