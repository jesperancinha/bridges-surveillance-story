# Bridge Logistics Guide

## Guide 1

### Tab 1 (Java 16)

1. `make build`
2. `make venv`
3. `source venv/bin/activate`
4. `make venv-install`
5. `make docker` - You should now see something like this

![make docker](./docs/images/Screenshot%202021-09-16%20at%2020.45.15.png "make docker")

6. `make logs-apps-server-tail` - If errors are reported it doesn't automatically mean that something is wrong. The apps container will try to make connections. If it keeps throwing exceptions, please restart the container with `make docker-apps`.

### Intellij / Command line (Java 11)

- Intellij or another IDE

Start [MeterReadingsLauncher](bl-central-server/bl-meters-readings-service/src/main/scala/org/jesperancinha/logistics/meters/readings/MetersReadingsLauncher.scala) and [PassengersReadingLauncher](bl-central-server/bl-passengers-readings-service/src/main/scala/org/jesperancinha/logistics/passengers.readings/PassengersReadingsLauncher.scala) outside the containers

- Command line (Use java 11)

1. `sdk use java 11.0.11.hs-adpt`
2. `java -jar bl-central-server/bl-passengers-readings-service/target/bl-passengers-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar &`
3. `java -jar bl-central-server/bl-meters-readings-service/target/bl-meters-readings-service-2.0.0-SNAPSHOT-jar-with-dependencies.jar &`

You can also just start `make start-readers`. This will start both processes. Just jake sure you are using JDK 11.

![make start-readers](./docs/images/Screenshot%202021-09-16%20at%2020.44.59.png "make start-readers")

### Tab 2 (Java 16)

1. `make docker-stats` - You should now see something like this

![make docker-stats](./docs/images/Screenshot%202021-09-16%20at%2020.44.16.png "make docker-stats")

### Tag 1 (Back to initial tab) (Java 16)

1. Stop tailing with `Ctrl-C`
2. `make demo` - Make sure you are still in `venv`. If not please run `source venv/bin/activate` again.

![make demo](./docs/images/Screenshot%202021-09-16%20at%2020.40.21.png "make demo")
