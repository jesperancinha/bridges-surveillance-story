# Bridge Logistics Merchandise Control Service (MCS)

## RabbitMQ administrator password

http://localhost:15672/

guest/guest

## Create RabbitMQ Federation

```bash
rabbitmq-plugins enable rabbitmq_federation
rabbitmq-plugins enable rabbitmq_federation_management
```

## Build
mvn clean package && docker build -t org.jesperancinha/bridge-logistics-mcs .

## Run

docker rm -f bridge-logistics-mcs || true && docker run -d -p 8080:8080 -p 4848:4848 --name bridge-logistics-mcs org.jesperancinha/bridge-logistics-mcs

## Configuration Files for RabbitMQ

/usr/local/sbin/rabbitmq-defaults

## Install RabbitMQ

-   Command line
```bash
brew install rabbitmq
export PATH=$PATH:/usr/local/sbin
vim ~/.bash_profile
```
-   Bash profile
```bash
#HOMEBREW RABBITMQ
export HOMEBREW_RABBITMQ=/usr/local/Cellar/rabbitmq/3.8.3/sbin/
export PATH=$PATH:$HOMEBREW_RABBITMQ
```

>NOTE: Port 1883 may be in use by another application. Most likely Mosquitto. In that case, please run:

```bash
brew services stop mosquitto
```

## Start RabbitMQ

```bash
rabbitmq-server
```

## Stop RabbiMQ

```bash
rabbitmqctl stop
```

## References

-   [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)
-   [Is it possible to run more than one rabbitmq instance on one machine?](https://stackoverflow.com/questions/21453910/is-it-possible-to-run-more-than-one-rabbitmq-instance-on-one-machine)
-   [RabbitMQ Federation Plugin](https://www.rabbitmq.com/federation.html)
-   [RabbitMQ Federated Exchanges](https://www.rabbitmq.com/federated-exchanges.html)
-   [RabbitMQ Federation](https://medium.com/trendyol-tech/rabbitmq-federation-plugin-cb87f7450365)
-   [RabbitMQ Management Plugin](https://www.rabbitmq.com/management.html)
-   [RabbitMQ Clustering Guide](https://www.rabbitmq.com/clustering.html)
-   [Running multiple RabbitMQ instances/servers on 1 machine](https://lazareski.com/multiple-rabbitmq-instances-on-1-machine)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
