FROM node:current-alpine3.14

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY dist /usr/local/bin/bl-bridge-temperature-coap/dist

COPY node_modules /usr/local/bin/bl-bridge-temperature-coap/node_modules

COPY entrypoint.sh /usr/local/bin

#CoAP
EXPOSE 5683

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
