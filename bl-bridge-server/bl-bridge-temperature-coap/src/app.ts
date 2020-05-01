import coap from "coap"

import {produce} from "./kafkaProducer"

console.log(process.argv)
let server = coap.createServer()
let strings = process.argv.slice(2);
let host = strings.length > 0 ? strings[0] : 'localhost'
console.log(host)

server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    produce(host, JSON.parse(req.payload.toString()))
    res.end(`You just said this! ${requestMessage}`)
})

server.listen(function () {
})
