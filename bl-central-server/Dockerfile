FROM eclipse-temurin:21-alpine

WORKDIR /root

ENV LANG=C.UTF-8

COPY entrypoint.sh /root

COPY bl-web-app/target/bl-web-app.jar /root/bl-web-app.jar

COPY bl-merchandise-data-collector/target/bl-merchandise-data-collector.jar /root/bl-merchandise-data-collector.jar

COPY bl-sensor-data-collector/target/bl-sensor-data-collector.jar /root/bl-sensor-data-collector.jar


ENTRYPOINT ["/root/entrypoint.sh"]

EXPOSE 9000
