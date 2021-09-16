# -*- coding: utf-8 -*-
import random
from random import randint

PASSENGER_ROOT = "./../bl-simulation-data/passengers/"

genders = list()
first_names = list()
last_names = list()

with open("%sgender.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        genders.append(line.rstrip())

with open("%sfirst-names.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        first_names.append(line.rstrip())

with open("%slast-names.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        last_names.append(line.rstrip())

def generate_passengers(quantity):
    passenger_json = []
    i = 1
    passenger_names = set()
    while len(passenger_json) != quantity:
        first_name = first_names[randint(0, len(first_names) - 1)]
        last_name = last_names[randint(0, len(last_names) - 1)]
        if passenger_names.__contains__(first_name + last_name):
            continue
        passenger_names.add(first_name + last_name)
        gender = genders[randint(0, len(genders) - 1)]
        non_desired_gender = "Cis Man" if non == 0 else "Cis Woman"
        while gender == non_desired_gender:
            gender = genders[randint(0, len(genders) - 1)]
        passenger = {}
        passenger.update({"id": i})
        passenger.update({"firstName": first_name})
        passenger.update({"lastName": last_name})
        passenger.update({"gender": gender})
        passenger_json.append(passenger)
        i += 1
    # print(json.dumps(passenger_json))
    return passenger_json


# This method is to be used in the storyline
def generate_storyline_passengers(quantity):
    if quantity < 2:
        print("🔴 - Please provide at least 3 passengers. Remember that in the storyline you have to provide at least "
              "1 Passenger steals the suitcase, the others are the secret agent and the bystanders")
    i=1
    passenger_json = []
    passenger_names = set()
    while len(passenger_json) != quantity:
        first_name = first_names[randint(0, len(first_names) - 1)]
        last_name = last_names[randint(0, len(last_names) - 1)]
        if passenger_names.__contains__(first_name + last_name):
            continue
        passenger_names.add(first_name + last_name)
        gender = genders[randint(0, len(genders) - 1)]
        passenger = {}
        passenger.update({"id": i})
        passenger.update({"firstName": first_name})
        passenger.update({"lastName": last_name})
        passenger.update({"gender": gender})
        passenger_json.append(passenger)
        i += 1
    # print(json.dumps(passenger_json))
    return passenger_json
