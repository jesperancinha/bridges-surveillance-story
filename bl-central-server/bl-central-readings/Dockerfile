FROM eclipse-temurin:21-alpine

WORKDIR /root

ENV LANG=C.UTF-8

COPY entrypoint.sh /root

COPY bl-meters-readings-service/target/bl-meters-readings-service-jar-with-dependencies.jar /root/bl-meters-readings-service.jar

COPY bl-passengers-readings-service/target/bl-passengers-readings-service-jar-with-dependencies.jar /root/bl-passengers-reading.jar

ENTRYPOINT ["/root/entrypoint.sh"]

EXPOSE 4040 4041
