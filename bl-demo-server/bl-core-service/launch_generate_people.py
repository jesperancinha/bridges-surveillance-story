import json
import os
import sys

sys.path.insert(1, os.path.abspath('bl-passenger-service'))
sys.path.insert(2, os.path.abspath('../bl-passenger-service'))

from generate_people import generate_storyline_passengers, generate_mother_name as generate_storyline_mother_name


def generate_all_passengers(passengers):
    return generate_storyline_passengers(passengers)


def generate_mother_name(last_name):
    return generate_storyline_mother_name(last_name)


if __name__ == '__main__':
    passengers = generate_storyline_passengers(10)
    with open('./../bl-simulation-data/passengers/passengers.json', 'w') as file:
        file.write(json.dumps(passengers))
