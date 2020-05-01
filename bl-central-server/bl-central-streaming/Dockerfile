FROM jesperancinha/je-rabbitmq:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY entrypoint.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

# RabbitMQ
EXPOSE 5672 15671 15672

# Cassandra
EXPOSE 9042