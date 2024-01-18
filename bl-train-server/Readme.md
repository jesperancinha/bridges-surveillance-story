# bl-train-server

## Build containers

### bl-train-server-01-rabbitmq

```bash
docker stop bl-train-container
docker rm bl-train-container
docker build -t jesperancinha/bl-train-server:0.0.1 .
docker run -d --name bl-train-container jesperancinha/bl-train-server:0.0.1
docker exec -it bl-train-container bash
```

## System Info

```bash
uname -a
```

## Start RabbitMQ

```bash
rabbitmq-server
```

## Stop RabbiMQ

```bash
rabbitmqctl stop
```

## Create RabbitMQ Federation

```bash
rabbitmq-plugins enable rabbitmq_federation
rabbitmq-plugins enable rabbitmq_federation_management
```

## RabbitMQ running with administrator password

[RabbitMQ Default Localhost](http://localhost:15672)

guest/guest

## Minishift [![alt text](dev/src/jofisaes/bridge-logistics/docs/bl-openshift-s.png)](https://manage.openshift.com/)

```bash
minishift start --vm-driver=virtualbox --memory=3G
```

```bash
minishift openshift config view
```

```bash
minishift openshift config set --patch '{"kubernetesMasterConfig": {"servicesNodePortRange": "1-30000"}}' --target master
```

```bash
oc apply -f bl-train-server-deployment.yaml
```

## References

-   [RABBIT MQ: CREATING QUEUE, EXCHANGE AND BINDINGS FROM A COMMAND LINE](https://www.scommerce-mage.com/blog/rabbitmq-creating-queues-and-bindings-from-a-command-line.html)
-   [Cluster migration with RabbitMQ Queue Federation](https://www.cloudamqp.com/blog/2015-07-08-migrate-between-plans-rabbitmq-queue-federation.html)
-   [Chapter 8.  Cross-cluster message distribution](https://livebook.manning.com/book/rabbitmq-in-depth/chapter-8/)
-   [Understand Federation in RabbitMQ - RabbitMQ Federation Plugin](https://jee-appy.blogspot.com/2018/08/setup-rabbitmq-exchange-federation.html)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
