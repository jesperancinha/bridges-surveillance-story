import {readdirSync, readFileSync} from "fs";
import kafka from 'kafka-node';
import {Config} from "./config";
import {Topics} from './topics';
import {Sample} from "./model/sample";

let produce = () => {
    try {
        const Producer = kafka.Producer;
        const client = new kafka.KafkaClient({kafkaHost: Config.kafka_server});
        const producer = new Producer(client);
        const kafka_topic = Topics.temperature;
        const files = readdirSync('points/');
        console.log(files);
        console.log(kafka_topic);
        const payloads = files.map(
            file => {
                let messages = JSON.parse(readFileSync(`points/${file}`, 'utf8')) as Sample
                messages.reading = "10"
                messages.timeOfReading = Date.now();
                return ({
                    topic: Topics.temperature,
                    messages: JSON.stringify(messages)
                })
            }
        )
        console.log(payloads);
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
