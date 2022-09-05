# Bridge Logistics Checkups

## 1. Troubleshooting

In order to make sure that the Demo runs smoothly please check the following

1. In your `/etc/hosts` you have this:

```shell
127.0.0.1       bl-central-kafka-server
```
3. Start the services in the containerized environment

```shell
make docker
```

4. Make sure to check everything after you start `make docker`:

```shell
make logs-cassandra-server
make logs-bridge-kafka-server
make logs-train-kafka-server
```

5. Restart the apps container

```shell
make docker-apps
```

6. If you see any error logs on the previous commands, please try the following commands:

```shell
make docker-dependent
make docker-train
make docker-cassandra
make docker-bridge
```

Recheck if they all started well this time and restart the apps container.

7. Start [MeterReadingsLauncher](bl-central-server/bl-meters-readings-service/src/main/scala/org/jesperancinha/logistics/meters/readings/MetersReadingsLauncher.scala) and [PassengersReadingLauncher](bl-central-server/bl-passengers-readings-service/src/main/scala/org/jesperancinha/logistics/passengers.readings/PassengersReadingsLauncher.scala) outside the containers

8. Enjoy the game! 🚂

---

## 2. Start the demo

```shell
source venv/bin/activate
make demo
```

## 3. Guides

Check the [Guide.md](./Guide.md) file for log recordings of how I started the Demo.

## 4. In detail

To start containers:

```shell
make docker
```

If you with to stop everything then run:

```shell
make stop
```

and if you want to remove everything then run:

```shell
make docker-delete
```

If you want to get everything clean please use:

```shell
make prune-all
```

Please check the [Makefile](./Makefile) and make sure you understand the available options before calling them.

Before running the demo you need to create a virtual environment in order to run Python 2 instead of Python 3:

```shell
make venv
source venv/bin/activate
make venv-install
```

Make sure you are still in virtual environment (venv). If not, run the command `source venv/bin/activate` again.
To start the demo please run:

```shell
make demo
```