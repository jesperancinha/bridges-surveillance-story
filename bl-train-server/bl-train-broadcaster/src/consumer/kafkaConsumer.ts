import kafka from 'kafka-node';
import {Config} from "../config";
import {Topics} from '../topics';

try {
    const Consumer = kafka.Consumer;
    const client = new kafka.KafkaClient({kafkaHost: Config.kafka_server});
    let consumer = new Consumer(
        client,
        [{topic: Topics.temperature, partition: 0}],
        {
            autoCommit: true,
            fetchMaxWaitMs: 1000,
            fetchMaxBytes: 1024 * 1024,
            encoding: 'utf8',
            fromOffset: false
        }
    );
    consumer.on('message', async function (message) {
        console.log('here');
        console.log(
            'kafka-> ',
            message.value
        );
    })
    consumer.on('error', function (err) {
        console.log('error', err);
    });
} catch (e) {
    console.log(e);
}