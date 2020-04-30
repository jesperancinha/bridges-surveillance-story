import mqtt from "mqtt";

var client = mqtt.connect('mqtt://127.0.0.1')
client.on('connect', function () {
    client.subscribe('humidity', function (err) {
        if (!err) {
            client.publish('humidity', 'Starting to Listen to MQTT messages...')
        }
    })
})

client.on('message', function (topic, message) {
    console.log(message.toString())
})
