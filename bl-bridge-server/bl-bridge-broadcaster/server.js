const cron = require("node-cron");
const express = require("express");
app = express();

cron.schedule("* * * * *", function() {
    console.log("Sending Kafka Messages");
});

app.listen(3128);