#!/bin/sh
mvn clean package && docker build -t org.jesperancinha/bridge-logistics-dcs .
docker rm -f bridge-logistics-dcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-dcs org.jesperancinha/bridge-logistics-dcs
