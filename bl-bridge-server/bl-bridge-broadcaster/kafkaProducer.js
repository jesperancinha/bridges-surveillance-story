const kafka = require('kafka-node');
const bp = require('body-parser');
const config = require('./config');
const topics = require('./topics');

let produce = () => {
    try {
        const Producer = kafka.Producer;
        const client = new kafka.KafkaClient({kafkaHost: config.kafka_server});
        const producer = new Producer(client);
        const kafka_topic = topics.temperature;
        console.log(kafka_topic);
        let payloads = [
            {
                topic: topics.temperature,
                messages: "Hello Hello Are you there?"
            }
        ];

        producer.on('ready', async function () {
            let push_status = producer.send(payloads, (err, data) => {
                if (err) {
                    console.log('[kafka-producer -> ' + kafka_topic + ']: broker update failed');
                } else {
                    console.log('[kafka-producer -> ' + kafka_topic + ']: broker update success');
                }
            });
        });

        producer.on('error', function (err) {
            console.log(err);
            console.log('[kafka-producer -> ' + kafka_topic + ']: connection errored');
            throw err;
        });
    } catch (e) {
        console.log(e);
    }
}

module.exports = {
    produce: produce
}
