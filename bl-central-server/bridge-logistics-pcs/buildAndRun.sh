#!/bin/sh
mvn clean package && docker build -t org.jesperancinha/bridge-logistics-pcs .
docker rm -f bridge-logistics-pcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-pcs org.jesperancinha/bridge-logistics-pcs
