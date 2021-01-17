import kafka from 'kafka-node';
import {Config} from "./config";
import {Topics} from './topics';

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
            // throw err;
        });
    } catch (e) {
        console.log(e);
    }
}

export {produce}
