FROM rabbitmq:3.9-management

WORKDIR /root

ENV LANG=C.UTF-8

RUN apt -y update

RUN apt install curl -y

RUN apt install python3 -y

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

COPY entrypoint.sh /root

#RabbitMQ
EXPOSE 5674 15674

ENTRYPOINT ["/root/entrypoint.sh"]
