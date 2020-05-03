# -*- coding: utf-8 -*-
import json
from random import randint

PASSENGER_ROOT = "../../bl-simulation-data/passengers/"

genders = list()
mother_names = list()
non_man_names = list()
non_woman_names = list()
last_names = list()

with open("%sgender.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        genders.append(line.rstrip())

with open("%smother-names.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        mother_names.append(line.rstrip())

with open("%snon-man.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        non_man_names.append(line.rstrip())

with open("%snon-woman.txt" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        non_woman_names.append(line.rstrip())

with open("%slast-names" % PASSENGER_ROOT) as file_in:
    for line in file_in:
        last_names.append(line.rstrip())


# Non man is 0 and non woman is 1
def generate_passengers(quantity=10):
    passenger_json = []
    i = 1
    passenger_names = set()
    while len(passenger_json) != quantity:
        non = randint(0, 2)
        first_names = non_man_names if non == 0 else non_woman_names
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
    print(json.dumps(passenger_json))
    return passenger_json


# Non man is 0 and non woman is 1
# This method is to be used in the storyline
# There has to be a least one Cis Man or Cis Woman and one passenger who is a Non Cis Man and also a Non Cis Woman
# The game goes about finding the bigot who murdered someone for being different
# These 2 first passengers aren't necessarily related to the crime, but there needs to be at least one of which in the game
def generate_storyline_passengers(quantity=10):
    if quantity < 2:
        print("🔴 - Please provide 2 passengers. Remember that in the storyline you have to provide at least 2 passengers. One is the victim, the other is the criminal")

    passenger_json = []
    passenger_names = set()

    non = randint(0, 2)
    first_names = non_man_names if non == 0 else non_woman_names
    first_name = first_names[randint(0, len(first_names) - 1)]
    last_name = last_names[randint(0, len(last_names) - 1)]
    gender = None
    non_desired_gender = "Cis Man" if non == 0 else "Cis Woman"
    while not gender or gender == non_desired_gender or (gender != "Cis Man" and gender != "Cis Woman"):
        gender = genders[randint(0, len(genders) - 1)]
    passenger = {}
    passenger.update({"id": 1})
    passenger.update({"firstName": first_name})
    passenger.update({"lastName": last_name})
    passenger.update({"gender": gender})
    passenger_json.append(passenger)

    found = False
    while not found:
        non = randint(0, 2)
        first_names = non_man_names if non == 0 else non_woman_names
        first_name = first_names[randint(0, len(first_names) - 1)]
        last_name = last_names[randint(0, len(last_names) - 1)]
        gender = genders[randint(0, len(genders) - 1)]
        while gender == "Cis Man" or gender == "Cis Woman":
            gender = genders[randint(0, len(genders) - 1)]
        passenger = {}
        passenger.update({"id": 2})
        passenger.update({"firstName": first_name})
        passenger.update({"lastName": last_name})
        passenger.update({"gender": gender})
        passenger_json.append(passenger)
        if not passenger_names.__contains__(first_name + last_name):
            found = True

    i = 3
    while len(passenger_json) != quantity:
        non = randint(0, 2)
        first_names = non_man_names if non == 0 else non_woman_names
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
    print(json.dumps(passenger_json))
    return passenger_json
