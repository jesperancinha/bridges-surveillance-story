# bl-bridge-merchandise-service

## Install RabbitMQ

-   Command line
```bash
brew install rabbitmq
export PATH=$PATH:/usr/local/sbin
vim ~/.bash_profile
```
-   Bash profile
```bash
#HOMEBREW RABBITMQ
export HOMEBREW_RABBITMQ=/usr/local/Cellar/rabbitmq/3.8.3/sbin/
export PATH=$PATH:$HOMEBREW_RABBITMQ
```

>NOTE: Port 1883 may be in use by another application. Most likely Mosquitto. In that case, please run:

```bash
brew services stop mosquitto
```

## Start RabbitMQ

```bash
rabbitmq-server
```

## Stop RabbiMQ

```bash
rabbitmqctl stop
```

## References

-   [Publish/Subscribe (using the Pika Python client)](https://www.rabbitmq.com/tutorials/tutorial-three-python.html)
-   [How to install RabbitMQ on Mac using Homebrew](https://www.dyclassroom.com/howto-mac/how-to-install-rabbitmq-on-mac-using-homebrew)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
