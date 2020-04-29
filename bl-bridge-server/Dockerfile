FROM jesperancinha/je-streams:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY entrypoint.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

#RabbitMQ
EXPOSE 5674 15671 15674

#CoAP
EXPOSE 5683

#Mosquittto
EXPOSE 1883

#Cassandra
EXPOSE 7199 7000 7001 9042 9160 61620 61621 8888