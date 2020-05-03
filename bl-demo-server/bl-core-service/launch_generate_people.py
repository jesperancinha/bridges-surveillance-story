import json
import os
import sys

sys.path.insert(1, os.path.abspath('../bl-passenger-service'))
sys.path.insert(2, os.path.abspath('bl-passenger-service'))

from generate_people import generate_passengers


if __name__ == '__main__':
    passengers = generate_passengers(170)
    with open('./../../bl-simulation-data/passengers/passengers.json','w') as file:
        file.write(json.dumps(passengers))
