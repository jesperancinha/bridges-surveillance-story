FROM rabbitmq:3.9

WORKDIR /root

ENV LANG=C.UTF-8

RUN apt -y update

RUN apt install curl -y

RUN apt install python3 -y

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY entrypoint.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

EXPOSE 5675 15671 15675