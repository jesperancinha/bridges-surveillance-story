b: build
build:
	mvn clean install
no-test:
	mvn clean install -DskipTests
start-readings:
	java --add-exports=java.base/jdk.internal.ref=ALL-UNNAMED --add-exports=java.base/sun.nio.ch=ALL-UNNAMED -jar target/bl-passengers-readings-service-jar-with-dependencies.jar
stop-readings:
	processAString=$(ps -fx | grep -e "bl-passengers-readings-service-jar-with-dependencies.jar" | grep -v "grep"); \
	processNumber="$(echo "${processAString}" | cut -d' ' -f2)"; \
	echo "${processNumber}"; \
	kill -9 "${processNumber}" &
