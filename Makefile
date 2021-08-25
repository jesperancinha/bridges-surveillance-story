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
stop:
	docker-compose down
prune-all:
	docker system prune --all