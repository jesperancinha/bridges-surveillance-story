# Bridge Service

You can find here installation information about how to get the services running

## RabbitMQ

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

## Sources

-   [How to install RabbitMQ on Mac using Homebrew](https://www.dyclassroom.com/howto-mac/how-to-install-rabbitmq-on-mac-using-homebrew)