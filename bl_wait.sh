#!/bin/bash
GITHUB_RUN_ID=${GITHUB_RUN_ID:-123}

function checkServiceByNameAndMessage() {
    name=$1
    message=$2
    docker-compose -p "${GITHUB_RUN_ID}" logs "$name" > "logs"
    string=$(cat logs)
    counter=0
    echo "Project $GITHUB_RUN_ID"
    echo -n "Starting service $name "
    while [[ "$string" != *"$message"* ]]
    do
      echo -e -n "\e[93m-\e[39m"
      docker-compose -p "${GITHUB_RUN_ID}" logs "$name" > "logs"
      string=$(cat logs)
      sleep 1
      counter=$((counter+1))
      if [ $counter -eq 200 ]; then
          echo -e "\e[91mFailed after $counter tries! Cypress tests may fail!!\e[39m"
          echo "$string"
          exit 1
      fi
    done
    counter=$((counter+1))
    echo -e "\e[92m Succeeded starting $name Service after $counter tries!\e[39m"
}

checkServiceByNameAndMessage bl-central-psql 'database system is ready to accept connections'
checkServiceByNameAndMessage bl-central-cassandra 'Starting listening for CQL clients on /0.0.0.0:9042'
checkServiceByNameAndMessage bl-bridge-01-mosquitto_server 'mosquitto version'
checkServiceByNameAndMessage bl-bridge-01-mosquitto_server 'running'
checkServiceByNameAndMessage bl-central-kafka-server 'started (kafka.server.KafkaServer)'
checkServiceByNameAndMessage bl-train-01-rabbitmq-server 'Setting permissions for user "test" in vhost "bl-train-01-sensor-vh"'
checkServiceByNameAndMessage bl-bridge-01-rabbitmq-server 'Setting permissions for user "test" in vhost "bl-bridge-01-sensor-vh"'
checkServiceByNameAndMessage bl-bridge-01-sensors-server 'bl-bridge-humidity-mqtt'
checkServiceByNameAndMessage bl-bridge-01-sensors-server 'bl-bridge-temperature-coap'
checkServiceByNameAndMessage bl-central-server-apps 'update bridge_opening_times set closing_time=?, opening_time=? where id=?'
checkServiceByNameAndMessage bl-central-server 'Setting policy "bl-bridge-01-sensor-policy" for pattern'
