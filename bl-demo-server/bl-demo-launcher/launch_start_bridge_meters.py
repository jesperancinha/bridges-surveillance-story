import json
import os
import sys
from os import listdir
from os.path import isfile, join

sys.path.insert(1, os.path.abspath('../bl-bridge-meter-service'))
sys.path.insert(2, os.path.abspath('bl-bridge-meter-service'))

from send_bridge_reading import send_meter

def lauch_bridge_meters(host):
    files_in_dir = [f for f in listdir('../bl-simulation-data/bridge') if isfile(join('../bl-simulation-data/bridge', f))]
    for file in files_in_dir:
        with open('../bl-simulation-data/bridge/' + file, 'r') as f:
            data = json.load(f)
            data.update({'reading': 10})
            send_meter(host, data)

if __name__ == '__main__':
    lauch_bridge_meters("127.0.0.1")
