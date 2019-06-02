#!/bin/sh
mvn clean package && docker build -t com.jesperancinha/bridge-logistics-dcs .
docker rm -f bridge-logistics-dcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-dcs com.jesperancinha/bridge-logistics-dcs 
