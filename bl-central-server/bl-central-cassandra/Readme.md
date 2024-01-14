## BL Cassandra image

Creating a cassandra image from scratch

## Docker

-   Making a build

```bash
docker build . -t blcassandra 
```

-   Running a container

```shell
docker run -d blcassandra -p 9042:9042
```

-   Monitoring and enabling

```shell
nodetool status
nodetool enablebinary
```

-   Cassandra configuration

Perform configuration for TEST only! (0.0.0.0 is not an acceptable production configuration)

```shell
sed -i 's/listen_address: localhost/listen_address: 0.0.0.0/g' cassandra.yaml 
```

## References

-   [Init script for Cassandra with docker-compose](https://newbedev.com/init-script-for-cassandra-with-docker-compose)
-   [Running Cassandra in Docker](https://www.datastax.com/learn/apache-cassandra-operations-in-kubernetes/running-a-cassandra-application-in-docker#docker-images)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
