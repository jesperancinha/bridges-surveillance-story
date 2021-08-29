## Bridge Logisics Installation Notes

## Installation notes

-   This installation has been tested with SDK-Man and Java version

```bash
sdk upgrade
sdk install 16.0.1.hs-adpt
sdk use 16.0.1.hs-adpt
```

- Cassandra resource consumption is very high and it competes with the RabbitMQ and Kafka streams of this demo. Considering common machines, you are expected to have an anonymous login able Cassandra installation on your machine

- Cassandra for MAC-OS (Make sure that when you start Cassandra, that you do so in a Java 8 environment):
				```bash
				brew install cassandra
				cassandra
				```
-   Hosts
				When making tests with spark agains the dockerized environment, Kafka will have to reply back. For that we need this mapping:
```text
127.0.0.1 bl_bridge_01_server
```

## Testing

-   [bridge-logistics-jms](bridge-logistics-jms/Readme.md)

## [Overview](dev/src/jofisaes/bridge-logistics/docs/Overview.md)

## [Requirements Change Log](dev/src/jofisaes/bridge-logistics/docs/ChangeLog.md)

## Status: [Under development](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/UnderConstruction.md)

## Docker installation

### Install VirtualBox

Option 1

>$ brew tap caskroom/cask  
>$ brew install brew-cask-completion  
>$ brew install brew-cask  
>$ brew cask install virtualbox

Option 2

Go to: [Download VirtualBox](https://www.virtualbox.org/wiki/Downloads)

You might need this:

>$ sudo spctl --master-enable
### Install Docker machine

Option 1

>$ brew cask install docker
>$ docker-machine create dev

Option 2

>Go to: [Install Docker Desktop for Mac](https://docs.docker.com/docker-for-mac/install/)

### Start Docker

>$ docker build --rm=true -t bridge-logistics-image .

>$ docker run -i -t -d -p 5432:5432 bridge-logistics-image

>$ docker container ls --all

## Apache Spark [![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/apache-spark-50.png "Apache Spark")](https://spark.apache.org/)

```bash
brew install apache-spark

spark-shell
```

NOTE: ONLY if everything else fails and you have Spark Consumers stuck in cache:
```bash
sudo rm -rf  /private/var/folders
```

## Zookeeper [![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/zookeeper-50.png "Zookeeper")](https://zookeeper.apache.org/)

```bash
brew install zookeeper

brew upgrade zookeeper

zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties

tail -f /usr/local/var/log/zookeeper/zookeeper.log
```

## Kafka [![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kafka-50.png "Kafka")](https://kafka.apache.org/documentation/streams/)

```bash
brew install kafka

brew upgrade kafka

kafka-server-start /usr/local/etc/kafka/server.properties

kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic TEMPERATURE
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic HUMIDITY
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic WINDSPEED
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic WINDDIRECTION
```

## Python libraries

```bash
pip install futures
pip install pebble
pip install pika
pip install coapthon
pip install paho-mqtt
pip install kafka-python
pip install requests
```

## Docker Troubleshooting

- Check logs

```bash
docker container logs --details bridge-logistics_bl_central_cassandra_1 
```

- List running containers

```shell
docker ps --format '{{.ID}} - {{.Names}}' 
```

- List all containers

```shell
docker ps -a  --format '{{.ID}} - {{.Names}}' 
```

- Stop all containers manually

```shell
docker ps --format '{{.ID}}' | xargs docker stop
```

## Remove postgres

### Mac-OS

```shell
cd /Library/PostgreSQL/11
cd /Library/PostgreSQL/13
open open uninstall-postgres.app
```
