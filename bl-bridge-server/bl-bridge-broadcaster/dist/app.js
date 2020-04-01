const kafkaProducer = require('./kafkaProducer');
const cron = require("node-cron");
const express = require("express");
let app = express();
function sendMessage() {
    console.log("Sending Kafka Messages");
    kafkaProducer.produce();
    console.log("Message sent!");
}
cron.schedule("* * * * *", function () {
    sendMessage();
});
sendMessage();
app.listen(3128);
//# sourceMappingURL=app.js.map