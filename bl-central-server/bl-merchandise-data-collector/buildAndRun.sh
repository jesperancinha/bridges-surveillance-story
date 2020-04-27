#!/bin/sh
mvn clean package && docker build -t org.jesperancinha/bridge-logistics-mcs .
docker rm -f bridge-logistics-mcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-mcs org.jesperancinha/bridge-logistics-mcs
