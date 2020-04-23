FROM jesperancinha/je-all-build-jdk-14:0.0.1

WORKDIR /root

ENV LANG=C.UTF-8

COPY entrypoint.sh /root

COPY bl-web-app/target/bl-web-app-*.jar /root/bl-web-app.jar

ENTRYPOINT ["/root/entrypoint.sh"]

EXPOSE 9000
