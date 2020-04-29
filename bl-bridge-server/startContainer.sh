#!/usr/bin/env bash

docker stop bl-bridge-server-container

docker rm bl-bridge-server-container

cd bl-bridge-humidity-coap

npm run build

cd ..

docker build . -t bl-bridge-server

docker run -d \
-p 5674:5672 \
-p 15674:15672 \
-p 5683:5683/udp \
-p 1883:1883 \
-p 7199:7199 \
-p 7001:7001 \
-p 7000:7000 \
-p 9042:9042 \
-p 9160:9160 \
-p 61620:61620 \
-p 61621:61621 \
-p 8888:8888 \
--name bl-bridge-server-container bl-bridge-server