import cron from "node-cron";
import express from "express";

let app = express();

function sendMessage() {
    console.log("Sending MQTT Temperature info...");
    console.log("Temperature data sent!");
}

cron.schedule("* * * * *", function () {
    sendMessage();
});

sendMessage();

app.listen(3128);