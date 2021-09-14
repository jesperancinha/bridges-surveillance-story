#!/usr/bin/env bash

cd bl-bridge-server/bl-bridge-temperature-coap || exit

yarn install

cd ..

cd bl-bridge-humidity-mqtt || exit

yarn install

cd ../..

cd bl-train-server/bl-train-people-mqtt || exit

yarn install

cd ../..
