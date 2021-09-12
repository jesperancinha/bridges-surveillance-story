FROM centos:7

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

RUN yum -y update

RUN yum -y install java

COPY datastax.repo  /etc/yum.repos.d/datastax.repo

RUN yum -y install dsc22

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

# Cassandra
EXPOSE 9042 7199

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

