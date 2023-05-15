MODULE_LOCATIONS := bl-meters-readings-service bl-passengers-readings-service

b: build
build:
	mvn clean install
no-test:
	mvn clean install -DskipTests
start-readings: no-test
	@for location in $(MODULE_LOCATIONS); do \
  		export CURRENT=$(shell pwd); \
  		echo "Starting services $$location..."; \
		cd $$location; \
	    make start-readings \
		cd $$CURRENT; \
	done
stop-readings:
	@for location in $(MODULE_LOCATIONS); do \
  		export CURRENT=$(shell pwd); \
  		echo "Stopping services $$location..."; \
		cd $$location; \
	    make stop-readings \
		cd $$CURRENT; \
	done

