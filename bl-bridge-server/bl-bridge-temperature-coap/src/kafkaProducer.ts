import kafka from 'kafka-node';
import {Config} from "./config";
import {Topics} from './topics';

let tries = 10;

let produce = (host: string, data: object = {}) => {
    try {
        const Producer = kafka.Producer;
        const client = new kafka.KafkaClient({kafkaHost: host + ":" + Config.kafka_port});
        const producer = new Producer(client);
        const kafka_topic = Topics.temperature;
        const jsonMessage = [{
            topic: Topics.temperature,
            messages: JSON.stringify(data)
        }]

        console.log(jsonMessage);
        producer.on('ready', async function () {
            tries = 100;
            let push_status = producer.send(jsonMessage, (err, data) => {
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
            if (tries-- < 0) {
                throw err;
            } else {
                sleep(1000).then(_ => produce(host, data))
            }
        });
    } catch (e) {
        if (tries-- > 0) {
            sleep(1000).then(_ => produce(host, data))
        }
        console.log(e);
    }
}

function sleep(ms) {
    return new Promise((resolve) => {
        setTimeout(resolve, ms);
    });
}

export {produce}
