FROM jesperancinha/je-rabbitmq:0.0.1

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

ENTRYPOINT rabbitmq-server

EXPOSE 5672 15671 15672
