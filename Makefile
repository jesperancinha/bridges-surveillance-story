build: build-npm
	cd bl-demo-server && python bl-core-service/launch_generate_people.py
	mvn clean install -Pdemo -DskipTests
build-npm:
	cd bl-bridge-server/bl-bridge-temperature-coap && yarn install && npm run build
	cd bl-bridge-server/bl-bridge-humidity-mqtt && yarn install && npm run build
	cd bl-train-server/bl-train-people-mqtt && yarn install && npm run build
build-maven:
	mvn clean install -Pdemo -DskipTests
test:
	mvn test
	cd bl-bridge-server/bl-bridge-temperature-coap && npm run test
	cd bl-bridge-server/bl-bridge-humidity-mqtt && npm run test
	cd bl-train-server/bl-train-people-mqtt && npm run test
test-maven:
	mvn test
local: no-test
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
docker-clean:
	docker-compose rm -svf
docker:
	rm -rf out
	docker-compose up -d --build --remove-orphans
docker-databases: stop local
build-images:
build-docker: stop no-test build-npm
	docker-compose up -d --build --remove-orphans
show:
	docker ps -a  --format '{{.ID}} - {{.Names}} - {{.Status}}'
logs-central-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server" | xargs docker logs
logs-central-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server" | xargs docker logs -f
logs-train-kafka-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker logs
logs-train-kafka-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker logs -f
logs-bridge-kafka-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker logs
logs-bridge-kafka-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker logs -f
logs-zookeeper-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_zookeeper_server" | xargs docker logs
logs-zookeeper-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_zookeeper_server" | xargs docker logs -f
logs-rabbitmq-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_rabbitmq_server" | xargs docker logs
logs-rabbitmq-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_rabbitmq_server" | xargs docker logs -f
logs-cassandra-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_cassandra" | xargs docker logs
logs-cassandra-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_cassandra" | xargs docker logs -f
docker-dependent:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker restart
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker restart
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_cassandra" | xargs docker restart
docker-train:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker start
docker-bridge:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker start
docker-cassandra:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_cassandra" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_cassandra" | xargs docker start
docker-stats:
	docker stats bl_central_server bl_central_server bl_central_kafka_server bl_vehicle_01_server bl_bridge_01_sensors_server bl_bridge_01_mosquitto_server bl_bridge_01_rabbitmq_server bl_central_kafka_server bl_central_psql bl_central_server_apps bl_central_cassandra bl_train_01_rabbitmq_server
docker-stats-simple:
	docker stats bl_central_server bl_central_server bl_central_kafka_server bl_vehicle_01_server bl_bridge_01_sensors_server bl_bridge_01_mosquitto_server bl_bridge_01_rabbitmq_server bl_central_kafka_server bl_central_psql bl_central_server_apps bl_train_01_rabbitmq_server
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=bl_" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs docker rm
docker-cleanup: docker-delete
	docker images -q | xargs docker rmi
	docker rmi bridge-logistics_bl_train_01_rabbitmq_server
	docker rmi bridge-logistics_bl_central_kafka_server
	docker rmi bridge-logistics_bl_train_01_zookeeper_server
	docker rmi bridge-logistics_bl_central_kafka_server
	docker rmi bridge-logistics_bl_bridge_01_rabbitmq_server
	docker rmi bridge-logistics_bl_bridge_01_temperature_coap_server
	docker rmi bridge-logistics_bl_bridge_01_humidity_mqtt_server
	docker rmi bridge-logistics_bl_vehicle_01_server
	docker rmi bridge-logistics_bl_central_server
	docker rmi bridge-logistics_postgres
	docker rmi bridge-logistics_bl_central_server_apps
docker-delete-apps: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server_apps" | xargs docker rm
	docker rmi bridge-logistics_bl_central_server_apps
prune-all: stop
	docker ps -a --format '{{.ID}}' -q | xargs docker stop
	docker ps -a --format '{{.ID}}' -q | xargs docker rm
	docker system prune --all
	docker builder prune
	docker system prune --all --volumes
stop:
	docker-compose down --remove-orphans
venv:
	pip install virtualenv
	pip install virtualenvwrapper
	virtualenv venv --python=python2.7
venv-install:
	pip install requests
	pip install pika
	pip install enum
	pip install kafka
	pip install coapthon
	pip install mqtt
	pip install paho-mqtt
	exit
demo:
	python bl-demo-server/launch_demo_server.py
