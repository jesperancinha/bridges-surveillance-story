const kafkaProducer = require('./kafkaProducer');
const cron = require("node-cron");
const express = require("express");
const kafka = require('kafka-node');
const bp = require('body-parser');
const config = require('./config');

app = express();

function sendMessage() {
    console.log("Sending Kafka Messages");
    kafkaProducer.produce()
    console.log("Message sent!");
}

cron.schedule("* * * * *", function () {
    sendMessage();
});

sendMessage();

app.listen(3128);