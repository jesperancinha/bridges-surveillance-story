build:
	./generateData.sh
	mvn clean install -Pdemo -DskipTests
	cd bl-bridge-server/bl-bridge-temperature-coap || exit
	npm run build
	cd ..
	cd bl-bridge-humidity-mqtt || exit
	npm run build
	cd ../..
	cd bl-train-server/bl-train-people-mqtt || exit
	npm run build
	cd ../..
test:
	mvn test
local: no-test
	mkdir -p bin
no-test:
	mvn clean install -DskipTests
docker:
	docker-compose up -d --build --remove-orphans
docker-databases: stop local
build-images:
build-docker: stop no-test
	docker-compose up -d --build --remove-orphans
stop:
	docker-compose down
