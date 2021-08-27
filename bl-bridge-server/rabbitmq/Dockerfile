FROM rabbitmq:3.9

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

ENTRYPOINT ["/root/entrypoint.sh"]

#RabbitMQ
EXPOSE 5674 15674

COPY entrypoint.sh /root
