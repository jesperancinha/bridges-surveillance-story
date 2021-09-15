# Bridge Logistics Guide

## Guide 1

### Tab 1 (Java 16)

1. `make build`
2. `make venv`
3. `source venv/bin/activate`
4. `make venv-install`
5. `make docker`
6. `make docker-apps`
7. `make logs-apps-server-tail` - If errors are reported it doesn't automatically mean that something is wrong. The apps container will try to make connections. If it keeps throwing exceptions, please restart the container with `make docker-apps`.

### Intellij / Command line (Java 11)

- Intellij or another IDE

Start [MeterReadingsLauncher](bl-central-server/bl-meters-readings-service/src/main/scala/org/jesperancinha/logistics/meters/readings/MetersReadingsLauncher.scala) and [PassengersReadingLauncher](bl-central-server/bl-passengers-readings-service/src/main/scala/org/jesperancinha/logistics/passengers.readings/PassengersReadingsLauncher.scala) outside the containers

- Command line (Use java 11)

1. `sdk use java 11.0.11.hs-adpt`
2. `java -jar bl-central-server/bl-passengers-readings-service/target/bl-passengers-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar &`
3. `java -jar bl-central-server/bl-meters-readings-service/target/bl-meters-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar &`

You can also just start `make start-readers`. This will start both processes. Just jake sure you are using JDK 11.

### Tab 2 (Java 16)

1. `make docker-stats`

### Tag 1 (Back to initial tab) (Java 16)

1. Stop tailing with `Ctrl-C`
2. `make demo` - Make sure you are still in `venv`. If not please run `source venv/bin/activate` again.
