FROM confluentinc/cp-kafka

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

COPY startKafka.sh /usr/local/bin/

COPY *.properties /opt/kafka/config/

COPY entrypoint.sh /usr/local/bin/

# Kafka
EXPOSE 9098 9099

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
