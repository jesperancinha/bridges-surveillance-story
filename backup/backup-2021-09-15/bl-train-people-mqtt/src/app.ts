import mqtt from "mqtt";
import {produce} from "./kafkaProducer";

console.log(process.argv)
let strings = process.argv.slice(2);
let host = strings.length > 0 ? strings[0] : 'localhost'
console.log(host)

const client = mqtt.connect('mqtt://127.0.0.1');
client.on('connect', function () {
    client.subscribe('people', function (err) {
        if (!err) {
            // client.publish('people', 'Starting to Listen to MQTT messages...')
            console.log('people', 'Starting to Listen to MQTT messages...')
        }
    })
})

client.on('message', function (topic, message) {
    let messageJson = JSON.parse(message.toString());
    console.log(messageJson)
    produce(host, messageJson)
})
