#!/usr/bin/env bash

cd bl-bridge-server/bl-bridge-temperature-coap || exit

yarn

cd ..

cd bl-bridge-humidity-mqtt || exit

yarn

cd ../..

cd bl-train-server/bl-train-people-mqtt || exit

yarn

cd ../..
