import json
import os
import random
import sys
from multiprocessing import Process
from os import listdir
from os.path import isfile, join
from time import sleep
import time

sys.path.insert(1, os.path.abspath('../bl-bridge-services'))
sys.path.insert(2, os.path.abspath('bl-bridge-services'))

from send_bridge_temperature_reading import send_meter


def send_bridge_temperature_meter_readings(host, f):
    data = json.load(f)
    data.update({'reading': random.randint(18, 28)})
    data.update({'timeOfReading':  int(time.time()*1000)})
    send_meter(host, data)


def start_bridge_meters(host):
    files_in_dir = [f for f in listdir('../bl-simulation-data/bridge') if isfile(join('../bl-simulation-data/bridge', f))]
    allTasks = []
    for file in files_in_dir:
        with open('../bl-simulation-data/bridge/' + file, 'r') as f:
            send_bridge_meter_simulation_process = Process(target=send_bridge_temperature_meter_readings, args=[host, f])
            send_bridge_meter_simulation_process.start()
            allTasks.append(send_bridge_meter_simulation_process)
    for task in allTasks:
        task.join()
    sleep(5)


if __name__ == '__main__':
    start_bridge_meters("127.0.0.1")
