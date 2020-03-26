#!/usr/bin/env bash
brew install apache-spark
brew install zookeeper
brew install kafka

brew upgrade zookeeper
brew upgrade kafka

cp ./bl-bridge-server/kafka/*.* /usr/local/etc/kafka/
cp ./bl-bridge-server/kafka/*.* /usr/local/etc/kafka/
cp ./bl-bridge-server/zookeeper/*.* /usr/local/etc/zookeeper/
