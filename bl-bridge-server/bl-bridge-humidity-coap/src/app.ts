import coap from "coap"

let server = coap.createServer()


server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    res.end(`You just said this! ${requestMessage}`)
})

server.listen(function () {
})
