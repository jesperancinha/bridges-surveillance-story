import coap from "coap"

import {produce} from "./kafkaProducer"

console.log(process.argv)
let server = coap.createServer()
let strings = process.argv.slice(2);
let host = strings.length > 0 ? strings[0] : 'localhost'
console.log(host)

server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    let success = false;
    while (!success) {
        try {
            produce(host, JSON.parse(req.payload.toString()))
            res.end(`You just said this! ${requestMessage}`);
            success = true;
        } catch (err) {
            sleep(1);
        }
    }
})

server.listen(function () {
})

function msleep(n) {
    Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, n);
}

function sleep(n) {
    msleep(n * 1000);
}