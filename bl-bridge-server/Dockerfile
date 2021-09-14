FROM node:current-alpine3.14

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY bl-bridge-temperature-coap/dist /usr/local/bin/bl-bridge-temperature-coap/dist

COPY bl-bridge-temperature-coap/node_modules /usr/local/bin/bl-bridge-temperature-coap/node_modules

COPY bl-bridge-humidity-mqtt/dist /usr/local/bin/bl-bridge-humidity-mqtt/dist

COPY bl-bridge-humidity-mqtt/node_modules /usr/local/bin/bl-bridge-humidity-mqtt/node_modules

COPY entrypoint.sh /usr/local/bin

#CoAP
EXPOSE 5683

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
