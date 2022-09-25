FROM rabbitmq:3.10.7-management

WORKDIR /root

ENV LANG=C.UTF-8

RUN apt -y update

RUN apt install curl -y

RUN apt install python3 -y

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

COPY entrypoint.sh /root

#RabbitMQ
EXPOSE 5675 15675

ENTRYPOINT ["/root/entrypoint.sh"]
