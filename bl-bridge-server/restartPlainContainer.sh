#!/usr/bin/env bash

docker stop bl-bridge-container
docker rm bl-bridge-container
docker rmi jesperancinha/bl-bridge-server:0.0.1
docker build -t jesperancinha/bl-bridge-server:0.0.1 .
docker run -d -p 5674:5672 -p 15674:15672 --name bl-bridge-container jesperancinha/bl-bridge-server:0.0.1
#docker inspect bl-bridge-container
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bl-bridge-container
