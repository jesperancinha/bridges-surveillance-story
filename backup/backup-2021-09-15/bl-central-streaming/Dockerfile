FROM rabbitmq:3.10.7-management

WORKDIR /root

ENV LANG=C.UTF-8

RUN apt -y update

RUN apt install curl -y

RUN apt install python3 -y

RUN apt -y upgrade

COPY entrypoint.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

# RabbitMQ
EXPOSE 5672 15672
