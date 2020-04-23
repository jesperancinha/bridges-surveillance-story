#!/usr/bin/env bash

java -jar --enable-preview -Dspring.profiles.active=demo bl-web-app.jar &

tail -f /dev/null
