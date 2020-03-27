# Bridge Server Broadcaster


## Kafka locations

-   On a MAC-OS:

```bash
/usr/local/etc/kafka/server.properties
/usr/local/var/lib/kafka-logs
```

## Testing

-   Producer

```bash
kafka-console-producer --broker-list localhost:9090,localhost:9091,localhost:9092 --sync --topic TEMPERATURE
```

-   Consumer

```bash
kafka-console-consumer --bootstrap-server localhost:9090 --topic TEMPERATURE --from-beginning
kafka-console-consumer --bootstrap-server localhost:9091 --topic TEMPERATURE --from-beginning
kafka-console-consumer --bootstrap-server localhost:9092 --topic TEMPERATURE --from-beginning
```
-   Topics

```bash
kafka-topics --list --bootstrap-server localhost:9090
kafka-topics --list --bootstrap-server localhost:9091
kafka-topics --list --bootstrap-server localhost:9092
```

## References

-   [Integrating Node.js Build Tools with Maven](https://auth0.com/blog/integrating-node-dot-js-build-tools-with-maven/)
-   [Getting started with Kafka and Node.js - Setup with example](https://thatcoder.space/getting-started-with-kafka-and-node-js-with-example/)
-   [How To Create and Run Scheduled Jobs with Node.js](https://www.digitalocean.com/community/tutorials/nodejs-cron-jobs-by-examples)
-   [Build Node.js RESTful APIs in 10 Minutes](https://www.codementor.io/@olatundegaruba/nodejs-restful-apis-in-10-minutes-q0sgsfhbd)
