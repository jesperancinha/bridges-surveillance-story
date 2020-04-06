FROM centos:centos8

WORKDIR /root

RUN yum -y install epel-release

RUN yum -y upgrade

RUN yum -y update

RUN yum -y install -y gcc-c++ make

RUN curl -sL https://rpm.nodesource.com/setup_13.x | bash -

RUN yum -y install nodejs

RUN yum -y install python3

RUN curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"

RUN python3 get-pip.py

RUN rm get-pip.py

RUN yum -y install wget

RUN curl -s https://packagecloud.io/install/repositories/rabbitmq/rabbitmq-server/script.rpm.sh | bash

RUN yum makecache -y --disablerepo='*' --enablerepo='rabbitmq_rabbitmq-server'

RUN yum -y install rabbitmq-server

ENTRYPOINT ["tail", "-f", "/dev/null"]
