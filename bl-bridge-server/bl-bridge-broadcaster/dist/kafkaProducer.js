"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = require("fs");
const kafka_node_1 = __importDefault(require("kafka-node"));
const config_1 = require("./config");
const topics_1 = require("./topics");
let produce = () => {
    try {
        const Producer = kafka_node_1.default.Producer;
        const client = new kafka_node_1.default.KafkaClient({ kafkaHost: config_1.Config.kafka_server });
        const producer = new Producer(client);
        const kafka_topic = topics_1.Topics.temperature;
        const files = fs_1.readdirSync('points/');
        console.log(files);
        console.log(kafka_topic);
        const payloads = files.map(file => {
            let messages = fs_1.readFileSync(`points/${file}`, 'utf8');
            return ({
                topic: topics_1.Topics.temperature,
                messages: messages
            });
        });
        console.log(payloads);
        producer.on('ready', function () {
            return __awaiter(this, void 0, void 0, function* () {
                let push_status = producer.send(payloads, (err, data) => {
                    if (err) {
                        console.log('[kafka-producer -> ' + kafka_topic + ']: broker update failed');
                    }
                    else {
                        console.log('[kafka-producer -> ' + kafka_topic + ']: broker update success');
                    }
                });
            });
        });
        producer.on('error', function (err) {
            console.log(err);
            console.log('[kafka-producer -> ' + kafka_topic + ']: connection errored');
            throw err;
        });
    }
    catch (e) {
        console.log(e);
    }
};
module.exports = {
    produce: produce
};
//# sourceMappingURL=kafkaProducer.js.map