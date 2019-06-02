# Bridge Logistics JMS module

You can find here installation information about how to get the services running

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

## Sources

-   [How to install RabbitMQ on Mac using Homebrew](https://www.dyclassroom.com/howto-mac/how-to-install-rabbitmq-on-mac-using-homebrew)