import cron from "node-cron";
import express from "express";

let app = express();

function sendMessage() {
    console.log("Sending pople info...");
    console.log("People info sent!");
}

cron.schedule("* * * * *", function () {
    sendMessage();
});

sendMessage();

app.listen(3128);