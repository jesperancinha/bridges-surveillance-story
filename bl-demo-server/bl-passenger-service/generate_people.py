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
    return passenger_json

passengers = generate_passengers(70)
print(json.dumps(passengers))
