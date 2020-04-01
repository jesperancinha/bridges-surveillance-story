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
const kafka_node_1 = __importDefault(require("kafka-node"));
const config_1 = require("../config");
const topics_1 = require("../topics");
try {
    const Consumer = kafka_node_1.default.Consumer;
    const client = new kafka_node_1.default.KafkaClient({ kafkaHost: config_1.Config.kafka_server });
    let consumer = new Consumer(client, [{ topic: topics_1.Topics.temperature, partition: 0 }], {
        autoCommit: true,
        fetchMaxWaitMs: 1000,
        fetchMaxBytes: 1024 * 1024,
        encoding: 'utf8',
        fromOffset: false
    });
    consumer.on('message', function (message) {
        return __awaiter(this, void 0, void 0, function* () {
            console.log('here');
            console.log('kafka-> ', message.value);
        });
    });
    consumer.on('error', function (err) {
        console.log('error', err);
    });
}
catch (e) {
    console.log(e);
}
//# sourceMappingURL=kafkaConsumer.js.map