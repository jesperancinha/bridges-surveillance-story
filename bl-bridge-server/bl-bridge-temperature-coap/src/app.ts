import coap from "coap"

import {produce} from "./kafkaProducer"

let server = coap.createServer()


server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    produce(JSON.parse(req.payload.toString()))
    res.end(`You just said this! ${requestMessage}`)
})

server.listen(function () {
})
