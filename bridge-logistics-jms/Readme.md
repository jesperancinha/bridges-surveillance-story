# Bridge Logistics JMS module

You can find here installation information about how to get the services running
## ActiveMQ Installation

$ brew install activemq

## Running with JBoss

This application is being tested with JBoss 8.0.0.Final
If you run accross this error:

```text
Caused by: org.jboss.msc.service.ServiceNotFoundException: Service service jboss.ejb.default-resource-adapter-name-service not found
```

Please make sure that your standalone.xml file located in <wildfly_home>/standalone/configuration has been replaced by standalone-full.xml. You need that in order to run the MDB's (Message driven beans).

In standalone.xml make sure your resources are correctly configured.   
Also make sure you download the right adapter for your JBoss/Wildfly configuration.  
I am currentlyt using the following:

1. [activemq-rar-5.10.0.rar](https://search.maven.org/search?q=a:activemq-rar) | [direct download](https://search.maven.org/remotecontent?filepath=org/apache/activemq/activemq-rar/5.15.9/activemq-rar-5.15.9.rar)
2. [Wildfly Server 16.0.0 Final](https://wildfly.org/downloads) | [direct download](https://download.jboss.org/wildfly/16.0.0.Final/wildfly-16.0.0.Final.zip)

Remember to run your wilfly version with  **--add-modules=java.se** as a VM switch.

Remove this from your standalone xml:

```xml
    <default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/ExampleDS" jms-connection-factory="java:jboss/DefaultJMSConnectionFactory" managed-executor-service="java:jboss/ee/concurrency/executor/default" managed-scheduled-executor-service="java:jboss/ee/concurrency/scheduler/default" managed-thread-factory="java:jboss/ee/concurrency/factory/default"/>

```
> standalone.xml (after overwritten by that all file)  
```xml
 <subsystem xmlns="urn:jboss:domain:resource-adapters:5.0">
    <resource-adapters>
        <resource-adapter id="activemq">
            <archive>
                activemq-rar-5.15.9.rar
            </archive>
            <transaction-support>XATransaction</transaction-support>
            <config-property name="ServerUrl">
                tcp://localhost:61616
            </config-property>
            <config-property name="UserName">
                defaultUser
            </config-property>
            <config-property name="UseInboundSession">
                false
            </config-property>
            <config-property name="Password">
                defaultPassword
            </config-property>
            <connection-definitions>
                <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactory" enabled="true" pool-name="ConnectionFactory">
                    <xa-pool>
                        <min-pool-size>1</min-pool-size>
                        <max-pool-size>20</max-pool-size>
                        <prefill>false</prefill>
                        <is-same-rm-override>false</is-same-rm-override>
                    </xa-pool>
                </connection-definition>
            </connection-definitions>
            <admin-objects>
                <admin-object class-name="org.apache.activemq.command.ActiveMQQueue" jndi-name="topic/PasssengerTopic" use-java-context="true" pool-name="test_queue">
                    <config-property name="PhysicalName">
                        testQueue
                    </config-property>
                </admin-object>
            </admin-objects>
        </resource-adapter>
    </resource-adapters>
</subsystem>
```
Please not that in this project, it is the presence of the jboss-deployment-structure.xml that activates the connection between JBoss and the JMS queues and topics.

## Sources

-   [How to install RabbitMQ on Mac using Homebrew](https://www.dyclassroom.com/howto-mac/how-to-install-rabbitmq-on-mac-using-homebrew)
-   [Error when deploying an .ear file containing an MDB to JBoss](https://stackoverflow.com/questions/15670322/error-when-deploying-an-ear-file-containing-an-mdb-to-jboss)
-   [JNDI adaptor for RabbitMQ integration in WildFly](https://github.com/isis2304/rabbitmq-wildfly-adaptor)
-   [40 Introduction to the Java Persistence API](https://javaee.github.io/tutorial/persistence-intro.html)
-   [How to run Wildfly 14 with java 11?](https://stackoverflow.com/questions/52852192/how-to-run-wildfly-14-with-java-11)
-   [Can not finish schema update: org.h2.jdbc.JdbcSQLException: Table & ldquo; PG_CLASS & rdquo; not found; SQL statement](https://www.codesd.com/item/can-not-finish-schema-update-org-h2-jdbc-jdbcsqlexception-table-pg-class-not-found-sql-statement.html)
-   [Could not complete schema update: org.h2.jdbc.JdbcSQLException: Table “PG_CLASS” not found; SQL statement](https://stackoverflow.com/questions/27694783/could-not-complete-schema-update-org-h2-jdbc-jdbcsqlexception-table-pg-class)

# Deprecated

## RabbitMQ Installation

There are some quarks while installing RabbitMQ on a MAC. Here is what I had to do:

$ brew install rabbitmq

Error: The `brew link` step did not complete successfully
The formula built, but is not symlinked into /usr/local
Could not symlink sbin/cuttlefish
/usr/local/sbin is not writable.

You can try again using:
  brew link rabbitmq
  
$ brew link rabbitmq

Linking /usr/local/Cellar/rabbitmq/3.7.15...
Error: Could not symlink sbin/cuttlefish
/usr/local/sbin is not writable.

$ sudo echo /usr/local/sbin >> /etc/paths.d/usr_local_sbin

$ sudo mkdir /usr/local/sbin

$ sudo chown <your_user_name>:admin /usr/local/sbin

$ brew link rabbitmq

## Rabbit MQ start

$ rabbitmq-server

## Rabbit MQ stop

## Rabbit MQ management

> Current exchanges

$ rabbitmqctl list_exchanges

> Current bindings

$ rabbitmqctl list_bindings

## License

```text
Copyright 2016-2019 João Esperancinha (jesperancinha)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## About me

-   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=joaofilipesabinoesperancinha.nl&color=informational)](http://joaofilipesabinoesperancinha.nl)

-   [![Twitter Follow](https://img.shields.io/twitter/follow/jofisaes.svg?label=%40jofisaes&style=social)](https://twitter.com/intent/follow?screen_name=jofisaes)

-   [![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=jesperancinha&style=social)](https://github.com/jesperancinha)

-   Free Code Camp [jofisaes](https://www.freecodecamp.org/jofisaes)

-   Hackerrank [jofisaes](https://www.hackerrank.com/jofisaes)

-   Acclaim Badges [joao-esperancinha](https://www.youracclaim.com/users/joao-esperancinha/badges)

---

-   Projects:

    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Time%20Disruption%20Studios&color=informational)](http://tds.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Image%20Train%20Filters&color=informational)](http://itf.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=MancalaJE&color=informational)](http://mancalaje.joaofilipesabinoesperancinha.nl/)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Google%20Apps&message=Joao+Filipe+Sabino+Esperancinha&color=informational)](https://play.google.com/store/apps/developer?id=Joao+Filipe+Sabino+Esperancinha)
---

-   [![GitHub Logo](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/JEsperancinhaOrg-32.png)](https://github.com/JEsperancinhaOrg)

    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Android&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate-android)[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate-android)](https://search.maven.org/search?q=itf.itf-chartizate-android)[![Download](https://api.bintray.com/packages/jesperancinha/maven/itf-chartizate-android/images/download.svg)](https://bintray.com/jesperancinha/maven/itf-chartizate-android/_latestVersion)[![GitHub release](https://img.shields.io/github/release-pre/JEsperancinhaOrg/itf-chartizate-android.svg)](https://github.com/JEsperancinhaOrg/itf-chartizate-android/releases)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20Java&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate-modules/tree/master/itf-chartizate-java)[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate-java)](https://search.maven.org/search?q=itf.itf-chartizate-java)[![GitHub release](https://img.shields.io/github/release-pre/JEsperancinhaOrg/itf-chartizate-modules.svg)](https://github.com/JEsperancinha/itf-chartizate-modules/releases)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=ITF%20Chartizate%20API&color=informational)](https://github.com/JEsperancinhaOrg/itf-chartizate/tree/master/itf-chartizate-api)[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.itf/itf-chartizate-api)](https://search.maven.org/search?q=itf.itf-chartizate-api)[![GitHub release](https://img.shields.io/github/release-pre/JEsperancinhaOrg/itf-chartizate.svg)](https://github.com/JEsperancinhaOrg/itf-chartizate/releases)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Core&color=informational)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-core)[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.parser/markdowner-core)](https://search.maven.org/search?q=parser.markdowner-core)[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/markdowner.svg)](https://github.com/jesperancinha/markdowner/releases)
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=GitHub&message=Markdowner%20Filter&color=informational)](https://github.com/jesperancinha/markdowner/tree/master/markdowner-filter)[![Maven Central](https://img.shields.io/maven-central/v/org.jesperancinha.parser/markdowner-filter)](https://search.maven.org/search?q=parser.markdowner-filter)[![GitHub release](https://img.shields.io/github/release-pre/jesperancinha/markdowner.svg)](https://github.com/jesperancinha/markdowner/releases)

-   Status page:
    -   [![Generic badge](https://img.shields.io/static/v1.svg?label=Homepage&message=Project%20Status&color=informational)](https://github.com/jesperancinha/project-signer/blob/master/project-signer-templates/Status.md)

---

-   Badges:

[![Oracle Certified Professional, Java SE 8 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-professional-java-se-8-programmer.png)](https://www.youracclaim.com/badges/92e036f5-4e11-4cff-9935-3e62266d2074/public_url)
[![Oracle Certified Associate, Java SE 8 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-associate-java-se-8-programmer.png)](https://www.youracclaim.com/badges/a206436d-6fd8-4ca1-8feb-38a838446ee7/public_url)
[![Oracle Certified Associate, Java SE 7 Programmer](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/oracle-certified-associate-java-se-7-programmer.png)](https://www.youracclaim.com/badges/f4c6cc1e-cb52-432b-904d-36d266112225/public_url)
[![Deep Learning](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/deep-learning.png)](https://www.youracclaim.com/badges/8d27e38c-869d-4815-8df3-13762c642d64/public_url)
[![Scala Programming for Data Science - Level 2](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/scala-programming-for-data-science-level-2.png)](https://www.youracclaim.com/badges/4e26d062-6587-4bcf-b111-04f039318888/public_url)
[![Scala Programming for Data Science - Level 1](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/scala-programming-for-data-science-level-1.png)](https://www.youracclaim.com/badges/2471e6a1-05f9-40a1-9f13-92806875b690/public_url)
[![Spark - Level 2](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/spark-level-2.png)](https://www.youracclaim.com/badges/11c9792f-3e3d-4d01-89e9-3a9c1567e76c/public_url)
[![Spark - Level 2](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/spark-level-1.png)](https://www.youracclaim.com/badges/8de731f7-60f7-4fef-8748-5888168c76cb/public_url)
[![Applied Data Science with Python - Level 2](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/applied-data-science-with-python-level-2.png)](https://www.youracclaim.com/badges/ef84482b-c3cd-47d2-8d04-d3dd2b0c8aa3/public_url)
[![Python for Data Science](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/python-for-data-science.png)](https://www.youracclaim.com/badges/3cedbc65-f74b-4d17-81a2-121f214f811f/public_url)
[![Big Data Foundations - Level 2](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/big-data-foundations-level-2.png)](https://www.youracclaim.com/badges/d5de47d2-156c-4864-8605-13531b0df62e/public_url)
[![Big Data Foundations - Level 1](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/big-data-foundations-level-1.png)](https://www.youracclaim.com/badges/d216b767-fe19-4e83-b54b-4e1ffd937111/public_url)
[![Hadoop Foundations - Level 1](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/hadoop-foundations-level-1.png)](https://www.youracclaim.com/badges/3f7d55b3-b070-4fd1-a733-30c33c64e9a0/public_url)
[![Data Science Foundations - Level 1](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/data-science-foundations-level-1.png)](https://www.youracclaim.com/badges/51deb903-266a-4cc6-a401-ea80a8aaaeaf/public_url)
[![Streaming Analytics Basics for Python Developers](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/streaming-analytics-basics-for-python-developers.png)](https://www.youracclaim.com/badges/0a76635a-e612-4fee-9c44-ccfc54e02d77/public_url)
[![IBM Cloud Essentials](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/ibm-cloud-essentials.png)](https://www.youracclaim.com/badges/4e19b1b7-034c-4406-ac36-0fcbe9c46db7/public_url)
[![IBM Blockchain Essentials](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/badges/ibm-blockchain-essentials.png)](https://www.youracclaim.com/badges/41c2b74c-668f-4d3a-a98c-8d6f5ce2757d/public_url)
