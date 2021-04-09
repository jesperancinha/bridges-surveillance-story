# Bridge Management Logistics

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9663f8cd66764af3b551e76c13ea7833)](https://www.codacy.com/gl/jesperancinha/bridge-logistics/dashboard?utm_source=gitlab.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/bridge-logistics&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/42f50b94-393c-4637-86cd-1ee6412995c6)](https://codebeat.co/projects/gitlab-com-jesperancinha-bridge-logistics-master)

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-50.png "Docker")](https://www.docker.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/docker-compose-50.png "Docker Compose")](https://docs.docker.com/compose/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/apache-spark-50.png "Apache Spark")](https://spark.apache.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/rabbit-mq-50.png "Rabbit MQ")](https://www.rabbitmq.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kafka-50.png "Kafka")](https://kafka.apache.org/documentation/streams/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/zookeeper-50.png "Zookeeper")](https://zookeeper.apache.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/openshift-50.png "OpenShift")](https://manage.openshift.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-50.png "Spring")](https://spring.io/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/spring-boot-50.png "Spring Boot")](https://spring.io/projects/spring-boot)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/postgres-50.png "Postgres")](https://www.postgresql.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/cassandra-50.png "Cassandra")](http://cassandra.apache.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/scala-50.png "Scala")](https://www.scala-lang.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/java-50.png "Java")](https://en.wikipedia.org/wiki/Java_(programming_language))
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/python-50.png "Python")](https://www.python.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/nodejs-50.png "NodeJS")](https://nodejs.org/en/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/bash-50.png "Bash")](https://www.gnu.org/software/bash/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/npm-50.png "NPM")](https://www.npmjs.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/yarn-50.png "Yarn")](https://yarnpkg.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/mosquitto-50.png "Mosquitto")](https://mosquitto.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/CoAP-50.png "CoAP")](https://coap.technology/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/mqtt-50.png "MQTT")](https://mqtt.org//)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/amqp-50.png "AMQP")](https://www.amqp.org/)

This application uses event sourcing to serve the logistics for a bridge management system.
This is what in general this project is responsible for

1.   Count passengers going through a bridge
2.   Register transport type
3.   Register merchandise before crossing the bridge
4.   Register events per configured range area
5.   Inform trains of the train Schedule changes

Passengers are registered by numbers and if they they carry extra merchandise or a bike
Transport can be a train, bus, boat, bike, truck, etc.
Merchandise should registered if it's destined to commercial exchanges.
Events can be anything that may happen in a configured range around the bridge

1. For passengers, a development area will be created called PCS(Passenger Control Service).  
2. For merchandise, a development area will be created called MCS(Merchandise Control Service).  
3. For bridge timetables and ranges, a development area will be created called DCS(Domain Control Service).  

This project is also the official support project of my article on medium:

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/swlh/the-streaming-bridges-a-kafka-rabbitmq-mqtt-and-coap-example-9077a598169)
[The streaming bridges — A Kafka, RabbitMQ, MQTT and CoAP example](https://medium.com/swlh/the-streaming-bridges-a-kafka-rabbitmq-mqtt-and-coap-example-9077a598169)

and my article on my blog page at:

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "joaofilipesabinoesperancinha.nl")](http://joaofilipesabinoesperancinha.nl/blog/blog4-jdk14-retrospective)
[JDK 14 record classes. A retrospective.](http://joaofilipesabinoesperancinha.nl/blog/blog4-jdk14-retrospective)

## A detective case

Since this project is about bridges and trains and because I love the [Murder on the Orient Express](https://www.imdb.com/title/tt0071877/) film and the tv series [The Bridge](https://www.imdb.com/title/tt1733785/?ref_=fn_al_tt_1), I've created a game about it. This game is mean't to be, not only entertaining, but also a way for you to learn the basics of various streaming mechanisms and master your skills. This is the setting:

> There has been a gruesome crime committed over a bridge. You are the detective.
> You have find out the murderer fast and accurately otherwise he will be at large and take over the world.
> The crime you will be looking it may look like a hate crime, but it can mean much more...

More details coming soon...

Steps:
1.   Go to PSQL database on schema `bllogistics` in table `trains_log`. Filter by `check_in_out='CHECKIN' or check_in_out='CHECKOUT'`
2.   Calculate the difference in weight
3.   Go to Cassandra database on keyspace `readings` in table `passengers`. Filter by the weight you find. These are the suspects
4.   If you only have one suspect. Then congratulations you have found the murderer.
5.   Type your answer in the following format `firstName` + ` ` + `lastName`

Note that the story I’ve created is purely fictional. Any similarity between events and the characters generated and the locations described is purely coincidental. It is practically impossible to make a random scenario that doesn’t have anything in common with anyone’s personal life. This is the reason why it is so important that the reader of this article understands that. This is also the reason why all the names in this exercise are automatically randomly generated, precisely to reduce the possibility of such similarities to occur.
You DO NEED to generate the names first. By running file [passenger_generator.py](./bl-simulation-data/passenger_generator.py), you will find 4 files in the [passengers](./bl-simulation-data/passengers) folder.
In this file, you will find automatically generated names.
If you want to make this more fun you can add your own chosen names.
Just remember that each line must be a single name.

## Modules

-   [bl-central-server](./bl-central-server): The central server containing all centralizable data
-   [bl-bridge-server](./bl-bridge-server): A server installed on each bridge
-   [bl-train-server](./bl-train-server): A server installed on each train
-   [bl-vehicle-server](./bl-vehicle-server): A server installed on each vehicle
-   [bl-timetable-generator](./bl-vehicle-server): Utility to generate the bridge timetables
-   [bl-demo-server](./bl-demo-server): This server ensures that a simulated train passes through the bridge
-   [bl-legacy](./bl-legacy): Legacy software intended useful to share, but not in use

## Installation notes

-   This installation has been tested with SDK-Man and Java version 

```bash
sdk upgrade
sdk install java 15.0.1.hs-adpt
sdk use java 15.0.1.hs-adpt
```

-   Cassandra resource consumption is very high and it competes with the RabbitMQ and Kafka streams of this demo. Considering common machines, you are expected to have an anonymous login able Cassandra installation on your machine

    -   Cassandra for MAC-OS (Make sure that when you start Cassandra, that you do so in a Java 8 environment):
        ```bash
        brew install cassandra
        cassandra
        ``` 

This application is inspired by the TV Series - [The Bridge](https://www.imdb.com/title/tt1733785/)

## Constraints

1. Vehicles which will cross the bridge when they are open. Bridges are considered to be open, when the bridge plates are down. If the bridge plates are up, then the bridge is said to be closed. Bridges may also be closed at other times and for other reasons. When a bridge is closed, it cannot be crossed, regardless of the state of its plates.
2. Trains go over static bridges which are mostly open. They can be closed for exceptional reasons.
3. When trains go over bridge, we need to know how long the whole train took to cross it.
4. When vehicles go over the bridge, we need to know how long vehicle took to cross it.
5. We also need to know the complete weight being passed accross the bridge in regards to merchansise.
6. We aso need to know the complete weight being passed accross the bridge in regards to people.
7. The exact number of people will be an aproximation and will be a result from a triangulation of passing through heat sensors and light sensors.
8. Time tables and merchandise exchanges will be done via RabbitMQ.
9. Sensor information will be sent via Kafka.
10. People data will be sent via Kafka Streams.
11. All Kafka streamed information will be handle via Apache Spark.
12. Bridge opening times are subject to conflict detection. Upon detecting one coflict between opening times. The bridge remains closed until the conflict becomes resolved.
13. Conflict registration changes state but never gets removed

## Installation

In [Documentation](./Documentation), you will find support materials for this project.

-   Hosts
When making tests with spark agains the dockerized environment, Kafka will have to reply back. For that we need this mapping:
```text
127.0.0.1 bl_bridge_01_server
```

## Testing

-   [bridge-logistics-jms](bridge-logistics-jms/Readme.md)

## [Overview](Documentation/Overview.md)

## [Requirements Change Log](Documentation/ChangeLog.md)

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

### Setting up OpenShift [![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/openshift-50.png)](https://manage.openshift.com/)

-   Open an account

    -   [Openshift online](https://manage.openshift.com/)

-   Setup OKD (Original Community Distribution of Kubernetes)

    -   [OKD](https://www.okd.io/index.html)

-   Install Minishift

```bash
brew cask install minishift
brew cask install --force minishift
minishift addons install --defaults
minishift addons enable admin-user
minishift start --vm-driver=virtualbox
brew install openshift-cli
oc adm policy --as system:admin add-cluster-role-to-user cluster-admin developer
minishift console
oc create rolebinding default-view --clusterrole=view --serviceaccount=mancalaje:default --namespace=mancalaje
```

## Python libraries

```bash
pip install futures
pip install pebble
pip install pika
pip install coapthon
pip install paho-mqtt
pip install kafka-python
```

## [Hints & Tricks](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Hints%26Tricks.md)

-   [pbcopy](http://sweetme.at/2013/11/17/copy-to-and-paste-from-the-clipboard-on-the-mac-osx-command-line/)

```bash
curl -L "http://coolsite.com" | pbcopy
```

-   [SDKMAN!](https://sdkman.io/install)

-   Install java versions with [SDKMan](https://sdkman.io/) for MAC-OS and Linux based systems

```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 8.0.242.hs-adpt
sdk install java 11.0.6.hs-adpt
sdk install java 12.0.2.hs-adpt
sdk install java 13.0.2.hs-adpt
sdk install java 14.0.0.hs-adpt
```

-   Install java versions without [SDKMan](https://sdkman.io/) for [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab).

```bash
apt-get -y update
apt-get -y upgrade
apt -y install apt-transport-https ca-certificates wget dirmngr gnupg software-properties-common
wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | apt-key add -
add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/
apt -y update
apt -y install openjdk-11-jdk
apt install openjdk-13-jdk
apt -y install adoptopenjdk-8-hotspot
apt -y autoremove
```

- .bashrc file to get Gradle, GitPrompt, [SDKMAN](https://sdkman.io/) and some handy aliases in a Windows environment with [MinGW](http://www.mingw.org/).

```bash
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java8="sdk use java 8.0.242.hs-adpt"
alias java11="sdk use java  11.0.6.hs-adpt"
alias java12="sdk use java 12.0.2.hs-adpt"
alias java13="sdk use java 13.0.2.hs-adpt"
alias java14="sdk use java 14.0.0.hs-adpt"
alias m2disable="rm ~/.m2/settings.xml"
alias m2enable="cp /your_repo_folder/settings.xml ~/.m2/"

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="/root/.sdkman"
[[ -s "/root/.sdkman/bin/sdkman-init.sh" ]] && source "/root/.sdkman/bin/sdkman-init.sh"
```

- .bashrc file to get Gradle, GitPrompt and some handy aliases in a Windows environment with [ubuntu prompt for windows](https://www.microsoft.com/en-us/p/ubuntu/9nblggh4msv6?activetab=pivot:overviewtab).

```bash
if [ -f "/root/.bash-git-prompt/gitprompt.sh" ]; then
    GIT_PROMPT_ONLY_IN_REPO=1
    source /root/.bash-git-prompt/gitprompt.sh
fi

alias java8="export JAVA_HOME=/usr/lib/jvm/adoptopenjdk-8-hotspot-amd64 && update-java-alternatives -s adoptopenjdk-8-hotspot-amd64"
alias java11="export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64 && update-java-alternatives -s java-1.11.0-openjdk-amd64"
if [[ -d /usr/lib/jvm/java-13-oracle ]]
then
    alias java13="export JAVA_HOME=/usr/lib/jvm/java-13-oracle && update-java-alternatives -s java-13-oracle"
elif [[ -d /usr/lib/jvm/java-1.13.0-openjdk-amd64 ]]
then
    alias java13="export JAVA_HOME=/usr/lib/jvm/java-1.13.0-openjdk-amd64 && update-java-alternatives -s java-1.13.0-openjdk-amd64"
else
    echo "Java13 not found!"
fi
```

-   Upgrade [YARN](https://yarnpkg.com/)

```bash
curl --compressed -o- -L https://yarnpkg.com/install.sh | bash
```

-   Remove Docker-machine

NOTE: This process will remove old docker-machine installations.
User [Docker-Desktop](https://www.docker.com/products/docker-desktop) instead.

```bash
brew uninstall docker-machine-driver-vmware
brew uninstall --force docker-machine
docker system prune -a
```

-   Python libraries

```bash
pip install futures
pip install pebble
pip install pika
pip install requests
pip install CoAPthon
pip install coapthon
pip install paho-mqtt
pip install kafka-python
```

-   Docker logs

```bash
docker container logs --details bridge-logistics_bl_central_cassandra_1 
```

## Domain

-   [UIC classification of goods wagons](https://en.wikipedia.org/wiki/UIC_classification_of_goods_wagons)
-   [DB Cargon freight wagons](https://nl.dbcargo.com/resource/blob/1430008/9767e97bb070ccbbf77efd84e7d64948/freight_wagon_catalog_v2011-data.pdf)
-   [How are freight cars classifed by IR?](https://www.irfca.org/faq/faq-stock2.html)
-   [Hornby Wagons](https://www.hornby.com/uk-en/)
-   [Goods wagon](https://en.wikipedia.org/wiki/Goods_wagon)
-   [Python online compiler](https://www.programiz.com/python-programming/online-compiler/)

## References

-   [JDK 17 - What's new features in Java 17](https://www.techgeeknext.com/java/java17-features)
-   [Spring Tips: Java 14 (or: Can Your Java Do This?)](https://spring.io/blog/2020/03/11/spring-tips-java-14-or-can-your-java-do-this)
-   [Share Link Generator!](http://sharelinkgenerator.com/)
-   [Docker Desktop for Mac](https://hub.docker.com/editions/community/docker-ce-desktop-mac)
-   [Kafka vs. RabbitMQ: Architecture, Performance & Use Cases](https://www.upsolver.com/blog/kafka-versus-rabbitmq-architecture-performance-use-case)
-   [Real-Time Analysis of Popular Uber Locations using Apache APIs: Spark Structured Streaming, Machine Learning, Kafka and MapR Database](https://mapr.com/blog/real-time-analysis-popular-uber-locations-spark-structured-streaming-machine-learning-kafka-and-mapr-db/)
-   [IoT architecture: building blocks and how they work](https://www.scnsoft.com/blog/iot-architecture-in-a-nutshell-and-how-it-works)
-   [Top 15 Standard IoT Protocols That You Must Know About](https://www.ubuntupit.com/top-15-standard-iot-protocols-that-you-must-know-about/)
-   [Using Apache Kafka as a Scalable, Event-Driven Backbone for Service Architectures](https://www.confluent.io/blog/apache-kafka-for-service-architectures/)
-   [MQTT](https://www.npmjs.com/package/mqtt)
-   [Internet of Things: Where Does the Data Go?](https://www.wired.com/insights/2015/03/internet-things-data-go/)
-   [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
-   [Apache Kafka Installation on Mac using Homebrew](https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273 )
-   [Offset Management For Apache Kafka With Apache Spark Streaming](https://blog.cloudera.com/offset-management-for-apache-kafka-with-apache-spark-streaming/)
-   [Confluent Tutorial: Creating a Streaming Data Pipeline](https://docs.confluent.io/current/streams/quickstart.html)
-   [Spark Streaming + Kafka Integration Guide (Kafka broker version 0.10.0 or higher)](https://spark.apache.org/docs/2.2.0/streaming-kafka-0-10-integration.html)
-   [Spark Streaming Programming Guide](https://spark.apache.org/docs/2.2.0/streaming-programming-guide.html)
-   [Apache Spark Tutorial](https://www.javatpoint.com/apache-spark-tutorial)
-   [Java EE vs Spring Testing](https://antoniogoncalves.org/2018/01/16/java-ee-vs-spring-testing/)
-   [Arquillian JUnit5 Hacks](https://github.com/OndroMih/arquillian-junit5-hacks)
-   [Java Libhunt Arquillian Alternatives](https://java.libhunt.com/arquillian-github-com-alternatives)
-   [Eclipse EE4J](https://projects.eclipse.org/projects/ee4j)
-   [Arquillian](http://arquillian.org/)
-   [Java™ EE at a Glance](https://www.oracle.com/java/technologies/java-ee-glance.html)
-   [JMS vs RabbitMQ](https://dzone.com/articles/jms-vs-rabbitmq)
-   [Get Started with RabbitMQ](https://www.rabbitmq.com/getstarted.html)
-   [Microservice Architecture by Kong](https://microservices.io/)
-   [Integrate ActiveMQ with WildFly](http://www.mastertheboss.com/jboss-server/jboss-jms/integrate-activemq-with-wildfly)
-   [SQL Server Table and Column Naming Conventions](https://www.codeproject.com/Articles/1065295/SQL-Server-Table-and-Column-Naming-Conventions)
-   [The Power of a Good SQL Naming Convention](https://www.xaprb.com/blog/2008/10/26/the-power-of-a-good-sql-naming-convention/)
-   [Integration Testing for Java EE](https://www.oracle.com/technetwork/articles/java/integrationtesting-487452.html)
-   [How to create Docker Images with a Dockerfile](https://www.howtoforge.com/tutorial/how-to-create-docker-images-with-dockerfile/)
-   [How to create a Docker image for PostgreSQL and persist data](https://www.andreagrandi.it/2015/02/21/how-to-create-a-docker-image-for-postgresql-and-persist-data/)
-   [Dockerize PostgreSQL](https://docs.docker.com/engine/examples/postgresql_service/)

## About me 👨🏽‍💻🚀🏳️‍🌈

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/JEOrgLogo-20.png "João Esperancinha Homepage")](http://joaofilipesabinoesperancinha.nl)
[![Twitter Follow](https://img.shields.io/twitter/follow/joaofse?label=João%20Esperancinha&style=social "Twitter")](https://twitter.com/joaofse)
[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/medium-20.png "Medium")](https://medium.com/@jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/google-apps-20.png "Google Apps")](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/sonatype-20.png "Sonatype Search Repos")](https://search.maven.org/search?q=org.jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/docker-20.png "Docker Images")](https://hub.docker.com/u/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/stack-overflow-20.png)](https://stackoverflow.com/users/3702839/joao-esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/reddit-20.png "Reddit")](https://www.reddit.com/user/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/credly-20.png "Credly")](https://www.credly.com/users/joao-esperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/devto-20.png "Dev To")](https://dev.to/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackernoon-20.jpeg "Hackernoon")](https://hackernoon.com/@jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeproject-20.png "Code Project")](https://www.codeproject.com/Members/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/github-20.png "GitHub")](https://github.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bitbucket-20.png "BitBucket")](https://bitbucket.org/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/gitlab-20.png "GitLab")](https://gitlab.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/bintray-20.png "BinTray")](https://bintray.com/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/free-code-camp-20.jpg "FreeCodeCamp")](https://www.freecodecamp.org/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hackerrank-20.png "HackerRank")](https://www.hackerrank.com/jofisaes)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codeforces-20.png "Code Forces")](https://codeforces.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codebyte-20.png "Codebyte")](https://coderbyte.com/profile/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codewars-20.png "CodeWars")](https://www.codewars.com/users/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/codepen-20.png "Code Pen")](https://codepen.io/jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/coursera-20.png "Coursera")](https://www.coursera.org/user/da3ff90299fa9297e283ee8e65364ffb)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/hacker-news-20.png "Hacker News")](https://news.ycombinator.com/user?id=jesperancinha)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/infoq-20.png "InfoQ")](https://www.infoq.com/profile/Joao-Esperancinha.2/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Articles&message=Across%20The%20Web&color=purple)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Articles.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Time%20Disruption%20Studios&color=6495ED)](http://tds.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Image%20Train%20Filters&color=6495ED)](http://itf.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=MancalaJE&color=6495ED)](http://mancalaje.joaofilipesabinoesperancinha.nl/)
[![Generic badge](https://img.shields.io/static/v1.svg?label=All%20Badges&message=Badges&color=red)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Badges.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=Status&message=Project%20Status&color=red)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Status.md)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Android&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate-android)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Java&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate-modules/tree/master/itf-chartizate-java)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20API&color=yellow)](https://github.com/JEsperancinhaOrg/itf-chartizate/tree/master/itf-chartizate-api)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Core&color=yellow)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-core)
[![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Filter&color=yellow)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-filter)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/linkedin-20.png "LinkedIn")](https://www.linkedin.com/in/joaoesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/xing-20.png "Xing")](https://www.xing.com/profile/Joao_Esperancinha/cv)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/tumblr-20.png "Tumblr")](https://jofisaes.tumblr.com/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/pinterest-20.png "Pinterest")](https://nl.pinterest.com/jesperancinha/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-20/quora-20.png "Quora")](https://nl.quora.com/profile/Jo%C3%A3o-Esperancinha)

## Achievements

[![Oracle Certified Professional, JEE 7 Developer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-ee-7-application-developer-100.png "Oracle Certified Professional, JEE7 Developer")](https://www.credly.com/badges/27a14e06-f591-4105-91ca-8c3215ef39a2)
[![Oracle Certified Professional, Java SE 11 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-11-developer-100.png "Oracle Certified Professional, Java SE 11 Programmer")](https://www.credly.com/badges/87609d8e-27c5-45c9-9e42-60a5e9283280)
[![Oracle Certified Professional, Java SE 8 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-8-programmer-100.png "Oracle Certified Professional, Java SE 8 Programmer")](https://www.credly.com/badges/92e036f5-4e11-4cff-9935-3e62266d2074)
[![Oracle Certified Associate, Java SE 8 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-associate-java-se-8-programmer-100.png "Oracle Certified Associate, Java SE 8 Programmer")](https://www.credly.com/badges/a206436d-6fd8-4ca1-8feb-38a838446ee7)
[![Oracle Certified Associate, Java SE 7 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-associate-java-se-7-programmer-100.png "Oracle Certified Associate, Java SE 7 Programmer")](https://www.credly.com/badges/f4c6cc1e-cb52-432b-904d-36d266112225)
[![Oracle Certified Junior Associate](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-foundations-associate-java-100.png "Oracle Certified Foundations Associate")](https://www.credly.com/badges/6db92c1e-7bca-4856-9543-0d5ed0182794)
