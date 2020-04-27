package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.sensor.collector.data.TrainLogDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@ConditionalOnProperty(name = "bridge.logistics.train.sensor.active",
    matchIfMissing = true)
public class TrainSensorReceiver {

    private final Gson gson;

    private CountDownLatch latch = new CountDownLatch(1);

    public TrainSensorReceiver(Gson gson) {
        this.gson = gson;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        TrainLogDto trainLogDto = gson.fromJson(messageString, TrainLogDto.class);
        System.out.println("Received <" + messageString + ">");
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}