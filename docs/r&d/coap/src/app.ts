const coap = require("coap");

console.log(process.argv);
let server = coap.createServer();
let strings = process.argv.slice(2);
let host = strings.length > 0 ? strings[0] : 'localhost'
console.log(host)
server.on('request', function (req, res) {
    let requestMessage = req.payload.toString();
    let success = false;
    while (!success) {
        try {
            console.log(requestMessage);
            console.log(req.payload.toString());
            res.end(`You just said this! ${requestMessage}`);
            success = true;
        } catch (err) {
            sleep(1);
        }
    }
})

server.listen(function () {
    console.log('temperature', 'Starting to Listen to COAP messages...')
});

function mSleep(n) {
    Atomics.wait(new Int32Array(new SharedArrayBuffer(4)), 0, 0, n);
}

function sleep(n) {
    mSleep(n * 1000);
}
