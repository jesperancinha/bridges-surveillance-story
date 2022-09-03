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
from send_bridge_timestamp import send_signal as send_bridge_signal
from send_merchandise import send_merchandise as send_train_merchandise
from launch_generate_people import get_all_passengers
from send_people_readings import send_people


def check_in_out(host, time_to_get_to_bridge, time_to_get_to_station, origin,
                 d_lat, d_lon, d_lat2, d_lon2,
                 passengers, train, special_agent, secret_spy):
    print("🚂 🛤 Train is underway. Just left central statin 🏫")
    success = False
    while (not success):
        try:
            send_merchandise_message(host, origin, train, 'LOADED')
            success = True
        except:
            print("🔴 Train Merchandise queue not ready yet. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)
    train_message_process = Process(target=pulses, args=[host, origin, d_lat, d_lon, train])
    train_passenger_process = Process(target=pulses_passengers, args=[host, origin, passengers])
    train_passenger_process.start()
    train_message_process.start()
    sleep(time_to_get_to_bridge)
    print("🚂 🌉 Train entering Bridge...")
    send_checkin_message(host, origin, train, secret_spy)
    print("🚂 Train Checked In!")

    sleep(5)
    train_message_process.terminate()

    send_checkout_message(host, origin, train, secret_spy, special_agent, passengers)
    print("🚂 Train Checked Out!")
    print("🚂 Train Leaving Bridge...")
    train_message_process = Process(target=pulses, args=[host, origin, d_lat2, d_lon2, train])
    train_message_process.start()
    sleep(time_to_get_to_station)
    train_message_process.terminate()
    train_passenger_process.terminate()


def current_time():
    return int(round(time.time() * 1000))


def get_train_check_in_out_data(coord, weight, carriage_id, status):
    return {
        'id': 1,
        'source': 'TRAIN',
        'type': status,
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon,
        'weight': weight,
        'carriageId': carriage_id
    }


def get_bridge_check_in_out_data(coord, status):
    return {
        'id': 2,
        'source': 'BRIDGE',
        'type': status,
        'timestamp': current_time(),
        'lat': coord.lat,
        'lon': coord.lon
    }


def pulses(host, origin, d_lat, d_lon, train):
    while True:
        sleep(1)
        origin.delta(d_lat, d_lon)
        send_merchandise_message(host, origin, train, 'INTRANSIT')


def pulses_passengers(host, origin, passengers):
    while True:
        sleep(10)
        send_passenger_messages(host, origin, 'INTRANSIT', passengers)


def send_passenger_messages(host, origin, status, passengers):
    for passenger in passengers:
        passenger.update({'lat': origin.lat})
        passenger.update({'lon': origin.lon})
        passenger.update({'status': status})
        passenger.update({'timeOfReading': current_time()})
    send_people(host, passengers)


def send_merchandise_message(host, origin, train, status):
    train[0].update({'status': status})
    train[0].update({'lat': origin.lat})
    train[0].update({'lon': origin.lon})
    for carriage in train[0]["composition"]:
        send_train_signal(host,
                          get_train_check_in_out_data(origin, carriage["weight"], carriage["carriageId"], 'INTRANSIT'))
    success = False
    while not success:
        try:
            send_train_merchandise(host, train)
            success = True
        except:
            print("🔴 Train Merchandise queue error. Press Ctr-C to stop. Retry in 10 seconds...")
            sleep(10)

    print("🚂 Train Merchandise sent! " + str(current_time()))
    print("🚂 Train location: " + str(origin))


def send_checkin_message(host, origin, train, secret_spy):
    toiletCarriageId = secret_spy["carriageId"] - 1
    carriageId = secret_spy["carriageId"]
    carriage_toilet = list(filter(lambda x: x["carriageId"] == toiletCarriageId, train[0]["composition"]))[0]
    carriage_current = list(filter(lambda x: x["carriageId"] == carriageId, train[0]["composition"]))[0]
    print("🚂 🌉 ⬅️ Carriage with toilet on CHECKIN" + str(carriage_toilet))
    print("🚂 🌉 ⬅️ Carriage without toilet CHECKIN" + str(carriage_current))
    for carriage in train[0]["composition"]:
        send_train_signal(host,
                          get_train_check_in_out_data(origin, carriage["weight"], carriage["carriageId"], 'CHECKIN'))
        send_bridge_signal(host, get_bridge_check_in_out_data(origin, 'CHECKIN'))
        print("🚂 🌉 ⬅️ Train Check In sent!")


def send_checkout_message(host, origin, train, secret_spy, special_agent, passengers):
    carriageId = secret_spy["carriageId"]
    toiletCarriageId = carriageId - 1
    carriage_toilet = list(filter(lambda x: x["carriageId"] == toiletCarriageId, train[0]["composition"]))[0]
    carriage_current = list(filter(lambda x: x["carriageId"] == carriageId, train[0]["composition"]))[0]
    print("🚂 🌉 ➡️ Carriage with toilet on CHECKOUT" + str(carriage_toilet))
    print("🚂 🌉 ➡️ Carriage without toilet on CHECKOUT " + str(carriage_current))
    carriage_toilet.update({"weight": carriage_toilet["weight"] + special_agent["weight"]})
    carriage_current.update({"weight": carriage_current["weight"] - special_agent["weight"] - secret_spy["weight"]})
    print("🚂 🌉 ➡️ Carriage with toilet after moving" + str(carriage_toilet))
    print("🚂 🌉 ➡️ Carriage without toilet after moving" + str(carriage_current))
    secret_spy.update({"carriageId": toiletCarriageId})
    special_agent.update({"carriageId": toiletCarriageId})
    passengers.remove(secret_spy)
    for carriage in train[0]["composition"]:
        send_train_signal(host,
                          get_train_check_in_out_data(origin, carriage["weight"], carriage["carriageId"], 'CHECKOUT'))
        send_bridge_signal(host, get_bridge_check_in_out_data(origin, 'CHECKOUT'))
        print("🚂 🌉 ➡️ Train Check Out sent!")


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
    print("🚂 bound to " + str(dest))
    print("🚂 leaves " + str(origin))
    print("🚂 goes to " + str(station))

    passengers = get_all_passengers()

    print("🚂 carries passengers " + str(passengers))

    for passenger in passengers:
        passenger.update({"unit": "kg"})
        passenger.update({"weight": random.randint(40, 300)})
    #
    # total_passenger_weight = functools.reduce(lambda a, b:
    #                                           a + int(b["weight"])
    #                                           , passengers, 0)

    with open('../bl-simulation-data/carriages.json') as carriages_json:
        with open('../bl-simulation-data/train.json') as trains_json:
            train = json.load(trains_json)
            train_composition = train[0]["composition"]
            carriages = train_composition
            no_package_carriages = filter(lambda x: not "products" in x, carriages)
            json_carriages = json.load(carriages_json)
            train_carriages = map(lambda y:
                                  filter(lambda z:
                                         z["id"] == y["carriageId"],
                                         json_carriages)[0], no_package_carriages)
            just_people_train_carriages = list(filter(lambda x: x["type"] == "people", train_carriages))
            total_carriages_number = len(just_people_train_carriages)
            current_carriage_index = 0
            for passenger in passengers:
                if not "carriageId" in passenger:
                    current_carriage = just_people_train_carriages[current_carriage_index]
                    passenger.update({"carriageId": current_carriage["id"]})
                    current_carriage_index += 1
                    if current_carriage_index >= total_carriages_number:
                        current_carriage_index = 0
            for carriage in train_composition:
                carriage_weight = functools.reduce(lambda a, b:
                                                   a + int(b["weight"])
                                                   , filter(lambda x: x["carriageId"] == carriage["carriageId"],
                                                            passengers), 0)
                carriage.update({"weight": carriage_weight})

            print("🚂 Generated train composition: " + str(train_composition))

            # Carriage 1 is hardcoded. This should change in future version
            shared_carriage = just_people_train_carriages[1]
            print(shared_carriage)
            all_not_in_toilet_passengers = list(filter(lambda x: x["carriageId"] == shared_carriage["id"], passengers))

            print("🚂 🕺🏻 All people not in the toilet carriage: " + str(all_not_in_toilet_passengers))
            source_passenger_list = list(all_not_in_toilet_passengers)
            special_agent = source_passenger_list[
                random.randint(0, len(source_passenger_list) - 1)]
            source_passenger_list.remove(special_agent)
            secret_spy = source_passenger_list[
                random.randint(0, len(source_passenger_list) - 1)]

            # Unblock this if you want to cheat 😉
            # print("🚂 Thief / Spy" + str(special_agent))
            # print("🚂 Secret Agent " + str(secret_spy))
            train[0]["composition"] = train_composition
            train_checkin_checkout_process = Process(target=check_in_out,
                                                     args=[host, time_to_get_to_bridge, time_to_get_to_station,
                                                           origin, d_lat, d_lon, d_lat2, d_lon2,
                                                           passengers, train, special_agent, secret_spy])

            print("Time to get to bridge - " + str(time_to_get_to_bridge))
            print("Time to get back to station - " + str(time_to_get_to_station))

            train_checkin_checkout_process.start()
            train_checkin_checkout_process.join()
            train_checkin_checkout_process.terminate()

            send_merchandise_message(host, origin, train, 'DELIVERED')
            send_passenger_messages(host, origin, "CHECKOUT", passengers)
            suspects = list(filter(lambda x: x["weight"] == secret_spy["weight"], passengers))

            # Unblock this if you want to cheat 😉
            # print(suspects)

            train_result_simulation = {}
            train_result_simulation.update({"secret_spy": secret_spy})
            train_result_simulation.update({"special_agent": special_agent})
            train_result_simulation.update({"suspects": suspects})

            print(train_result_simulation)
            print("🚂 Arrived at the train central station! 🏫")
            return train_result_simulation
