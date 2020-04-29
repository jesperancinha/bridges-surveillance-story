FROM jesperancinha/je-streams:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY bl-bridge-humidity-coap/dist /root/dist

COPY bl-bridge-humidity-coap/node_modules /root/node_modules

COPY entrypoint.sh /root

RUN rm /usr/local/etc/kafka/config/server.properties

COPY kafka/*.* /usr/local/etc/kafka/config/

COPY kafka/*.* /usr/local/etc/kafka/config/

COPY zookeeper/*.* /usr/local/etc/kafka/config/

COPY startKafka.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

#RabbitMQ
EXPOSE 5674 15671 15674

#CoAP
EXPOSE 5683

#Mosquittto
EXPOSE 1883

#Cassandra
EXPOSE 7199 7000 7001 9042 9160 61620 61621 8888

#Kafka
EXPOSE 9090 9091 9092