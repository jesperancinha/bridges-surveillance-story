import coap from "coap"

import {produce} from "./kafkaProducer"

let server = coap.createServer()


server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    produce()

    res.end(`You just said this! ${requestMessage}`)
})

server.listen(function () {
})
