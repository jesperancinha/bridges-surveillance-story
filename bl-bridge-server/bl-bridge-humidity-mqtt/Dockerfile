FROM node:current-alpine3.14

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY dist /usr/local/bin/bl-bridge-humidity-mqtt/dist

COPY node_modules /usr/local/bin/bl-bridge-humidity-mqtt/node_modules

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

COPY entrypoint.sh /usr/local/bin
