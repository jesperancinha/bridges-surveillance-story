FROM jesperancinha/je-streams:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY entrypoint.sh /root

RUN rm /usr/local/etc/kafka/config/server.properties

COPY kafka/*.* /usr/local/etc/kafka/config/

COPY zookeeper/*.* /usr/local/etc/kafka/config/

COPY startKafka.sh /root

COPY startZookeeper.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

# RabbitMQ
EXPOSE 5673 15673

# Kafka
EXPOSE 9098 9099 9100 9101