#!/bin/bash

function checkServiceByNameAndMessage() {
    name=$1
    message=$2
    docker-compose logs "$name" > "logs"
    string=$(cat logs)
    counter=0
    while [[ "$string" != *"$message"* ]]
    do
      printf "."
      docker-compose logs "$name" > "logs"
      string=$(cat logs)
      sleep 1
      counter=$((counter+1))
      if [ $counter -eq 200 ]; then
          echo "Failed after $counter tries! Cypress tests may fail!!"
          echo "$string"
          exit 1
      fi
    done
    counter=$((counter+1))
    echo "Succeeded $name Service after $counter tries!"
}

checkServiceByNameAndMessage bl_central_psql 'database system is ready to accept connections'
checkServiceByNameAndMessage bl_central_cassandra 'Starting listening for CQL clients on /0.0.0.0:9042'
checkServiceByNameAndMessage bl_bridge_01_mosquitto_server 'mosquitto version 2.0.11 running'
checkServiceByNameAndMessage bl_central_kafka_server 'started (kafka.server.KafkaServer)'
checkServiceByNameAndMessage bl_train_01_rabbitmq_server 'Setting permissions for user "test" in vhost "bl_train_01_sensor_vh"'
checkServiceByNameAndMessage bl_bridge_01_rabbitmq_server 'Setting permissions for user "test" in vhost "bl_bridge_01_sensor_vh"'
checkServiceByNameAndMessage bl_bridge_01_sensors_server 'Starting to Listen to MQTT '
checkServiceByNameAndMessage bl_central_server_apps 'update bridge_opening_times set closing_time=?, opening_time=? where id=?'
checkServiceByNameAndMessage bl_central_server 'Setting policy "bl_bridge_01_sensor_policy" for pattern'
