#!/usr/bin/env bash

./generateData.sh

mvn clean install -Pdemo -DskipTests

cd bl-bridge-server/bl-bridge-temperature-coap

npm run build

cd ..

cd bl-bridge-humidity-mqtt

npm run build

cd ../..

cd bl-train-server/bl-train-people-mqtt

npm run build

cd ../..
