import express from "express";
import mqtt from "mqtt";

let app = express();

function startListeners() {
    var client = mqtt.connect('mqtt://127.0.0.1')
    client.on('connect', function () {
        client.subscribe('moisture', function (err) {
            if (!err) {
                client.publish('moisture', 'Starting to Listen to MQTT messages...')
            }
        })
    })

    client.on('message', function (topic, message) {
        console.log(message.toString())
    })
}

startListeners();

app.listen(3128);