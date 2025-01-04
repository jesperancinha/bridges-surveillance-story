import {Config} from "./config";
import { expect, test } from '@jest/globals';


test('config should be expected', () => {
    expect(Config.kafka_port).toBe('9092')
});
