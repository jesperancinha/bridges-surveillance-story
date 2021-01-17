#!/usr/bin/env bash

docker stop bl-central-container
docker rm bl-central-container
docker rmi jesperancinha/bl-central-server:0.0.1
docker build -t jesperancinha/bl-central-server:0.0.1 .
docker run -d -p 5672:5672 -p 15672:15672 --name bl-central-container jesperancinha/bl-central-server:0.0.1
#docker inspect bl-central-container
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bl-central-container
