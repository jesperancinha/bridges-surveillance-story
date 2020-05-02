FROM jesperancinha/je-streams:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

RUN rabbitmq-plugins enable --offline rabbitmq_management

RUN rabbitmq-plugins enable rabbitmq_federation

RUN rabbitmq-plugins enable rabbitmq_federation_management

RUN rabbitmq-plugins enable rabbitmq_shovel rabbitmq_shovel_management

COPY entrypoint.sh /root

COPY startKafka.sh /root

COPY startZookeeper.sh /root

ENTRYPOINT ["/root/entrypoint.sh"]

EXPOSE 5673 15671 15673