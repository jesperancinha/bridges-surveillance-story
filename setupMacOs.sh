#!/usr/bin/env bash
brew install apache-spark
brew install zookeeper
brew install kafka

brew upgrade zookeeper
brew upgrade kafka

./copyFiles.sh