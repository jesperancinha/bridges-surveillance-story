# Bridge Logistics Bridge Sensor Server

[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/mosquitto-50.png)](https://mosquitto.org/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/CoAP-50.png)](https://coap.technology/)
[![alt text](https://raw.githubusercontent.com/jesperancinha/project-signer/master/project-signer-templates/icons-50/kafka-50.png "Kafka")](https://kafka.apache.org/documentation/streams/)

## MQTT - Temperature
-   Installation

```bash
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
brew install mosquitto
```

-   Auto Start/Stop Mosquitto

```bash
brew services start mosquitto
brew services stop mosquitto
```

-   Remove Background service

```bash
mosquitto -c /usr/local/etc/mosquitto/mosquitto.conf
```

-   In one console

```bash
npm install mqtt -g
mqtt sub -t 'temperature' -h 'test.mosquitto.org' -v
mqtt sub -t 'temperature' -h 'localhost' -v
```
-   In another console

```bash
mqtt pub -t 'temperature' -h 'test.mosquitto.org' -m 'HOT - 100C'
mqtt pub -t 'temperature' -h 'localhost' -m 'HOT - 100C'
```

> NOTE: We can find all of these tests in [Mosquitto](http://test.mosquitto.org/) 🦟
## CoAP Client

```bash
npm install coap-cli -g
echo -n 'hello world' | coap post coap://localhost/message
```
## References

-   [mcollina/node-coap](https://github.com/mcollina/node-coap)
-   [vency/coap-cli](https://github.com/avency/coap-cli)

## About me

[![GitHub followers](https://img.shields.io/github/followers/jesperancinha.svg?label=Jesperancinha&style=for-the-badge&logo=github&color=grey "GitHub")](https://github.com/jesperancinha)
