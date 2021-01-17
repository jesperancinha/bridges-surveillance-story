#!/usr/bin/env bash

cd bl-bridge-server/bl-bridge-temperature-coap

yarn install

cd ..

cd bl-bridge-humidity-mqtt

yarn install

cd ../..

cd bl-train-server/bl-train-people-mqtt

yarn install

cd ../..
