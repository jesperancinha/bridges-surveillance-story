# Bridge Logistics Review logs

## Roadmap to v3.0.0

- Visual Angular Front-End interface
- Change hardcoded 10 people limitation
- Use a Log framework and remove System outs
- Create more carriages and make 4 and 3 random carriages
- Kotest tests
- Mockk tests
- Python unit tests
- Extend Scala tests
- Update Libraries further

## Roadmap to v2.0.0

- Official release after article text review

2021/09/17

2021/09/16 - Pre-Release
- Prepare release
- Change storyline
  - Original storyline too complicated and difficult to understand
  - The theme had too many distracting elements from the goal of the story
  - The simple puzzle introduced did not hold up to the story and the events didn't add up
- Remove unnecessary elements to the case:
  - Vehicle
- Re-enable all disabled tests

2021/09/15
- Update to rabbitmq source docker image to `rabbitmq:3.9-management`
- Fix error `management api returned status code 500 - exchange rabbitmq`
- Review [InstallationNotes.md](./docs/InstallationNotes.md) document
- Update instructions
- Cleanup and removal of unused code

2021/09/14
- Update Scala dependencies
- Make Cassandra stable
- Include Apache Spark in docker images
- Error: 
```java
Caused by: java.lang.reflect.InaccessibleObjectException: 
								Unable to make private java.nio.DirectByteBuffer(long,int) accessible: 
module java.base does not "opens java.nio" to unnamed module @654b5005
```
								
2021/09/11
- Remove EmbeddedPostgres and use TestContainers
- Update and organize dependencies
- Make Demo play easy and seamless
- Remove GSON due to incompatibility issue
- Replace custom containers
- Re-dockerize by splitting

2021/08/25 
- Update to JDK16
- Kotlin, Kotest, Mockk

2021/08/24 - Remove legacy projects

## Roadmap to v1.0.0

2020/05/18 - Release

2019/06/13
-   Rest services implementation foundations
-   Module separation

2019/06/12:
-   Automatic PostgreSQL table generation via JEE8 and the Java Persistence API
-   Docker file

2019/06/10:
-   Migration to Java 11
-   Docker foundations

2019/06/06:
-   Main entities created
-   Introduction of Lombok

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

## Made with

```bash
git for-each-ref --format="%(refname:short) | %(creatordate)" "refs/tags/*"
```
