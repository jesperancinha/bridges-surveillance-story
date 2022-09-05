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
build-npm-cypress:
	cd e2e && yarn
build-maven: create-demo-data
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
	docker logs bl-central-server
logs-central-server-tail:
	docker logs -f bl-central-server
logs-apps-server:
	docker logs bl-central-server-apps
logs-apps-server-tail:
	docker logs -f bl-central-server-apps
logs-central-kafka-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker logs
logs-central-kafka-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker logs -f
logs-zookeeper-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-train-01-zookeeper-server" | xargs docker logs
logs-zookeeper-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-train-01-zookeeper-server" | xargs docker logs -f
logs-rabbitmq-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-train-01-rabbitmq-server" | xargs docker logs
logs-rabbitmq-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-train-01-rabbitmq-server" | xargs docker logs -f
logs-cassandra-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-cassandra" | xargs docker logs
logs-cassandra-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-cassandra" | xargs docker logs -f
docker-apps:
	docker restart bl-central-server-apps
docker-dependent:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker restart
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker restart
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-cassandra" | xargs docker restart
docker-train:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker start
docker-bridge:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-kafka-server" | xargs docker start
docker-cassandra:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-cassandra" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-cassandra" | xargs docker start
docker-stats:
	docker stats bl-central-server bl-central-server bl-central-kafka-server bl-bridge-01-sensors-server bl-bridge-01-mosquitto_server bl-bridge-01-rabbitmq-server bl-central-kafka-server bl-central-psql bl-central-server-apps bl-central-cassandra bl-train-01-rabbitmq-server
docker-stats-simple:
	docker stats bl-central-server bl-central-server bl-central-kafka-server bl-bridge-01-sensors-server bl-bridge-01-mosquitto_server bl-bridge-01-rabbitmq-server bl-central-kafka-server bl-central-psql bl-central-server-apps bl-train-01-rabbitmq-server
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=bl-" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-" | xargs -I {} docker stop {}
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-" | xargs -I {} docker rm {}
docker-cleanup: docker-delete
	docker images -q | xargs docker rmi
	docker rmi bridge-logistics-bl-train-01-rabbitmq-server
	docker rmi bridge-logistics-bl-central-kafka-server
	docker rmi bridge-logistics-bl-train-01-zookeeper-server
	docker rmi bridge-logistics-bl-central-kafka-server
	docker rmi bridge-logistics-bl-bridge-01-rabbitmq-server
	docker rmi bridge-logistics-bl-bridge-01-temperature_coap_server
	docker rmi bridge-logistics-bl-bridge-01-humidity_mqtt_server
	docker rmi bridge-logistics-bl-vehicle-01-server
	docker rmi bridge-logistics-bl-central-server
	docker rmi bridge-logistics_postgres
	docker rmi bridge-logistics-bl-central-server-apps
docker-action:
	docker-compose -f docker-compose.yml up -d
docker-delete-apps: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl-central-server-apps" | xargs docker rm
	docker rmi bridge-logistics-bl-central-server-apps
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
venv-install:
	pip install virtualenv
	pip install virtualenvwrapper
venv:
	virtualenv venv --python=python3.7
# Start Python Env - https://www.python.org/downloads/release/python-2718/
install-python37-macos:
	brew install python@3.7
install:
	pip3 install requests
	pip3 install pika
	#pip3 install enum
	pip3 install kafka-python
	pip3 install coapthon
	pip3 install mqtt
	pip3 install paho-mqtt
	pip3 install --upgrade pip
# End Python Env
end-logs:
	docker-compose logs --tail 100
demo:
	python3 bl-demo-server/launch_demo_server.py
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
	docker-compose stop bl-central-kafka-server
	docker-compose rm bl-central-kafka-server
	docker-compose build --no-cache bl-central-kafka-server
	docker-compose up -d
build-readers:
	docker-compose stop bl-readers
	docker-compose rm bl-readers
	docker-compose build --no-cache bl-readers
	docker-compose up -d
dcd:
	docker-compose down --remove-orphans
	docker-compose rm -fsva
	docker volume ls -qf dangling=true | xargs -I {} docker volume rm  {}
dcp:
	docker-compose stop
dcup: dcd docker-clean docker bl-wait
dcup-full-action: dcd docker-clean build-maven build-npm docker bl-wait
dcup-action: dcp docker-action bl-wait
dcup-light: dcd
	docker-compose up -d bl-central-psql
