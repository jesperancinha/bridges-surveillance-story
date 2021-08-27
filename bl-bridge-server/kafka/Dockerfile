FROM bitnami/kafka:2.8.0

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY startKafka.sh /usr/local/bin/

COPY *.properties /opt/bitnami/kafka/config/

COPY entrypoint.sh /usr/local/bin/

#Kafka
EXPOSE 9092 9093 9094

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
