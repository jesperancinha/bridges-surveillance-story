import mqtt from "mqtt";
import {produce} from "./kafkaProducer";

console.log(process.argv)
let strings = process.argv.slice(2);
let host = strings.length > 0 ? strings[0] : 'localhost'
console.log(host)

const client = mqtt.connect('mqtt://bl-bridge-01-mosquitto_server');
client.on('connect', function () {
    client.subscribe('humidity', function (err) {
        if (!err) {
            // client.publish('humidity', 'Starting to Listen to MQTT messages...')
            console.log('humidity', 'Starting to Listen to MQTT messages...')
        }
    })
})

client.on('message', function (topic, message) {
    let messageJson = JSON.parse(message.toString());
    console.log(messageJson)
    produce(host, messageJson)
})
