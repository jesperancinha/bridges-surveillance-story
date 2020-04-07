#!/usr/bin/env bash

docker stop bl-train-container
docker rm bl-train-container
docker build -t jesperancinha/bl-train-server:0.0.1 .
docker run -d -p 5672:5672 -p 15672:15672 --name bl-train-container jesperancinha/bl-train-server:0.0.1
docker inspect bl-train-container
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bl-train-container
