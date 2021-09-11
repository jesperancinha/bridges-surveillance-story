FROM centos:7

WORKDIR /usr/local/bin

ENV LANG=C.UTF-8

RUN curl -OL http://apache.mirror.digitalpacific.com.au/cassandra/4.0.1/apache-cassandra-4.0.1-bin.tar.gz

RUN gpg --print-md SHA256 apache-cassandra-4.0.1-bin.tar.gz

RUN tar -xzvf apache-cassandra-4.0.1-bin.tar.gz

RUN yum install java-11-openjdk-devel -y

COPY entrypoint.sh /usr/local/bin/entrypoint.sh

# Cassandra
EXPOSE 9042

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]

