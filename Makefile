b: build
coverage-npm:
	cd bl-bridge-server/bl-bridge-humidity-mqtt && yarn && jest --coverage
	cd bl-bridge-server/bl-bridge-temperature-coap && yarn && jest --coverage
coverage-python:
	coverage run --source=bl-demo-server -m pytest && coverage json -o coverage-demo.json
	coverage run --source=bl-simulation-data -m pytest && coverage json -o coverage-simulation.json
coverage-maven:
	mvn clean install jacoco:prepare-agent package jacoco:report
coverage: coverage-npm coverage-python coverage-maven
build-npm:
	cd bl-bridge-server/bl-bridge-temperature-coap && yarn && npm run build
	cd bl-bridge-server/bl-bridge-humidity-mqtt && yarn && npm run build
build-maven:
	mvn clean install -Pdemo -DskipTests
build-python:
	cd bl-demo-server && python bl-core-service/launch_generate_people.py
build: build-npm build-python build-maven
test-maven:
	mvn test
local: no-test
	mkdir -p bin
test-node:
	cd bl-bridge-server/bl-bridge-temperature-coap && npm run test
	cd bl-bridge-server/bl-bridge-humidity-mqtt && npm run test
test: test-maven test-node
no-test:
	mvn clean install -DskipTests
docker-clean: docker-delete
	docker-compose rm -svf
docker:
	rm -rf out
	docker-compose up -d --build --remove-orphans
docker-restart: stop-jars stop docker
start-readers: stop-jars
	cd bl-central-server/bl-central-readings/bl-passengers-readings-service && make start-readings &
	cd bl-central-server/bl-central-readings/bl-meters-readings-service && make start-readings &
docker-databases: stop local
build-images:
build-docker: stop b
	docker-compose up -d --build --remove-orphans
clean-docker: docker-clean docker
show:
	docker ps -a  --format '{{.ID}} - {{.Names}} - {{.Status}}'
logs-central-server:
	docker logs bl_central_server
logs-central-server-tail:
	docker logs -f bl_central_server
logs-apps-server:
	docker logs bl_central_server_apps
logs-apps-server-tail:
	docker logs -f bl_central_server_apps
logs-central-kafka-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_kafka_server" | xargs docker logs
logs-central-kafka-server-tail:
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
docker-apps:
	docker restart bl_central_server_apps
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
	docker stats bl_central_server bl_central_server bl_central_kafka_server bl_bridge_01_sensors_server bl_bridge_01_mosquitto_server bl_bridge_01_rabbitmq_server bl_central_kafka_server bl_central_psql bl_central_server_apps bl_central_cassandra bl_train_01_rabbitmq_server
docker-stats-simple:
	docker stats bl_central_server bl_central_server bl_central_kafka_server bl_bridge_01_sensors_server bl_bridge_01_mosquitto_server bl_bridge_01_rabbitmq_server bl_central_kafka_server bl_central_psql bl_central_server_apps bl_train_01_rabbitmq_server
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=bl_" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs -I {} docker stop {}
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs -I {} docker rm {}
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
docker-action:
	docker-compose -f docker-compose.yml up -d
docker-delete-apps: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server_apps" | xargs docker rm
	docker rmi bridge-logistics_bl_central_server_apps
prune-all: stop
	docker ps -a --format '{{.ID}}' -q | xargs docker stop
	docker ps -a --format '{{.ID}}' -q | xargs docker rm
	docker system prune --all
	docker builder prune
	docker system prune --all --volumes
stop: stop-jars
	docker-compose down --remove-orphans
stop-jars:
	./stopRunningJars.sh
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
update:
	cd bl-bridge-server/bl-bridge-humidity-mqtt && npx browserslist --update-db && ncu -u && yarn
	cd bl-bridge-server/bl-bridge-temperature-coap && npx browserslist --update-db && ncu -u && yarn
npm-test:
	cd bl-bridge-server/bl-bridge-humidity-mqtt && npm run coverage
	cd bl-bridge-server/bl-bridge-temperature-coap && npm run coverage
report:
	mvn omni-coveragereporter:report
cypress-install:
	npm i -g cypress
	cd e2e && make build
cypress-open:
	cd e2e && make cypress-open
cypress-open-docker:
	cd e2e && make cypress-open-docker
cypress-electron:
	cd e2e && make cypress-electron
cypress-chrome:
	cd e2e && make cypress-chrome
cypress-firefox:
	cd e2e && make cypress-firefox
cypress-firefox-full:
	cd e2e && make cypress-firefox-full
cypress-edge:
	cd e2e && make cypress-edge
local-pipeline: build-maven build-npm test-maven test-node coverage report
bl-wait:
	bash bl_wait.sh
create-demo-data:
	cd bl-simulation-data && make create-demo-data
	cd bl-demo-server && make create-demo-data
build-kafka:
	docker-compose stop bl_central_kafka_server
	docker-compose rm bl_central_kafka_server
	docker-compose build --no-cache bl_central_kafka_server
	docker-compose up -d
build-readers:
	docker-compose stop bl_readers
	docker-compose rm bl_readers
	docker-compose build --no-cache bl_readers
	docker-compose up -d
dcd:
	docker-compose down --remove-orphans
	docker-compose rm -fsva
	docker volume ls -qf dangling=true | xargs -I {} docker volume rm  {}
dcp:
	docker-compose stop
dcup: dcd docker-clean docker bl-wait
dcup-full-action: dcd docker-clean create-demo-data build-maven build-npm docker bl-wait
dcup-action: dcp docker-action bl-wait
dcup-light: dcd
	docker-compose up -d bl_central_psql
