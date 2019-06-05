[![Build Status](https://travis-ci.org/jesperancinha/bridge-logistics.svg?branch=master)](https://travis-ci.org/jesperancinha/bridge-logistics)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/318e7f47f8944b3d97a83d2fd2402b85)](https://www.codacy.com/app/jofisaes/bridge-logistics?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jesperancinha/bridge-logistics&amp;utm_campaign=Badge_Grade)
[![CircleCI](https://circleci.com/gh/jesperancinha/bridge-logistics.svg?style=svg)](https://circleci.com/gh/jesperancinha/bridge-logistics)
[![codebeat badge](https://codebeat.co/badges/a81b5c40-aeca-4b8d-9015-1ed1004617e5)](https://codebeat.co/projects/github-com-jesperancinha-bridge-logistics-master)

# Bridge Management Logistics

Starting from the example of [Michel Schudel](https://github.com/MichelSchudel), this is an application to be build using a JMS messaging system to serve the logistics for a bridge management system.  
If you look at the first [brick](https://github.com/jesperancinha/bridge-logistics/blob/master/bridge-logistics-jms/src/main/java/com/jesperancinha/bridgelogistics/services/BridgeOpeningService.java) of this project, you can see that the whole project involves managing timetables as a kick-start.
The project will be expanded to further goals.  

Current goal is to use ActiveMQ as a JMS provider. 
JMS provider will play following roles:

1.   Count passengers going through a bridge
2.   Register transport type
3.   Register merchandise before crossing the bridge
4.   Register events per configured range area

Passengers are registered by numbers and if they they carry extra merchandise or a bike
Transport can be a train, bus, boat, bike, truck, etc.
Merchandise should registered if it's destined to commercial exchanges.
Events can be anything that may happen in a configured range around the bridge

For passengers, a development area will be created called PCS(Passenger Control Service).  
For merchandise, a development area will be created called MCS(Merchandise Control Service).  
For bridge ranges, a development area will be created called DCS(Domain Control Service).  

## Overview

![N|Solid](docs/BridgeSystem.png)

## Status 

**Under development**

## Location

[GitHub location](https://github.com/jesperancinha/brugdemo)

## Sources

-   [Brug Demo](https://github.com/MichelSchudel/brugdemo)
-   [JMS vs RabbitMQ](https://dzone.com/articles/jms-vs-rabbitmq)
-   [Get Started with RabbitMQ](https://www.rabbitmq.com/getstarted.html)
-   [Microservice Architecture by Kong](https://microservices.io/)
-   [Integrate ActiveMQ with WildFly](http://www.mastertheboss.com/jboss-server/jboss-jms/integrate-activemq-with-wildfly)
-   [SQL Server Table and Column Naming Conventions](https://www.codeproject.com/Articles/1065295/SQL-Server-Table-and-Column-Naming-Conventions)

## Requirements Change log

2019/06/05:
-   Design stages

2019/06/04:
-   First successful implementation of the JMS topics with ActiveMQ
-   Connection from Wildfly to local ActiveMQ

2019/06/03:   
-   Switch to ActiveMQ  

2019/06/02:
-   MDB's deployed
-   Foundations for Web application
-   First PassengerConsumer JMS Topic listener
-   Introduction of JMS provider
-   Introduction of RabbitMQ
-   JEE8 introduced
-   Requirements expansion

2019/06/01:
-   Product modularization
-   Records timetables per bridge name
-   Generates conflict table by key opening time
-   Orders opening times by opening time

2019/05/31:
-   Adds automated code QA tests
-   Changes code from dutch to english
-   AssertJ for unit tests
-   Groups by conflictual bridge opening
-   Groups by Bridge

## License

```text
Copyright 2019 João Esperancinha (jesperancinha)

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

-   Twitter [@jofisaes](https://twitter.com/jofisaes)
-   GitHub [jesperancinha](https://github.com/jesperancinha)
-   Free Code Camp [jofisaes](https://www.freecodecamp.org/jofisaes)
-   Hackerrank [jofisaes](https://www.hackerrank.com/jofisaes)