FROM confluentinc/cp-kafka:7.9.1

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY startKafka.sh /usr/local/bin/

COPY *.properties /opt/kafka/config/

COPY entrypoint.sh /usr/local/bin/

#Kafka
EXPOSE 9092 9093

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
