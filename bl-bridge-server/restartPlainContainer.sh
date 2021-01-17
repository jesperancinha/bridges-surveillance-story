#!/usr/bin/env bash

docker stop bl-bridge-server-container
docker rm bl-bridge-server-container
docker rmi bl-bridge-server
docker build -t bl-bridge-server .
docker run -d -p 5674:5672 -p 15674:15672 --name bl-bridge-server-container bl-bridge-server
#docker inspect bl-bridge-server-container
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bl-bridge-server-container
