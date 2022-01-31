import {Config} from "./config";

test('config should be expected', () => {
    expect(Config.kafka_port).toBe('9092')
});
