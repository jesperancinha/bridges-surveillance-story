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
docker:
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
logs-kafka-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_kafka_server" | xargs docker logs
logs-kafka-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_kafka_server" | xargs docker logs -f
logs-zookeeper-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_zookeeper_server" | xargs docker logs
logs-zookeeper-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_zookeeper_server" | xargs docker logs -f
logs-rabbitmq-server:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_rabbitmq_server" | xargs docker logs
logs-rabbitmq-server-tail:
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_train_01_rabbitmq_server" | xargs docker logs -f
docker-delete-idle:
	docker ps --format '{{.ID}}' -q --filter="name=bl_" | xargs docker rm
docker-delete: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs docker stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_" | xargs docker rm
docker-cleanup: docker-delete
	docker rmi bridge-logistics_bl_train_01_rabbitmq_server
	docker rmi bridge-logistics_bl_train_01_kafka_server
	docker rmi bridge-logistics_bl_train_01_zookeeper_server
	docker rmi bridge-logistics_bl_vehicle_01_server
	docker rmi bridge-logistics_bl_central_server
	docker rmi bridge-logistics_postgres
docker-delete-apps: stop
	docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server_apps" | xargs docker rm
prune-all: stop
	docker system prune --all
stop:
	docker-compose down --remove-orphans

