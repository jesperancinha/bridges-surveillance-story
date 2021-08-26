# bl-central-streaming

These are the central server RabbitMQ streams

## Open endpoints

- [Main page](http://localhost:15672/) - [http://localhost:15672/](http://localhost:15672/) - 

## Logs shortcut

```shell
docker ps -a --format '{{.ID}}' -q --filter="name=bl_central_server" | xargs docker logs
```