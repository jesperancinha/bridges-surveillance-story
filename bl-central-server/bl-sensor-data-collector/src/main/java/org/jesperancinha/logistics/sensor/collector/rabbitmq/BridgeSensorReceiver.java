package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.jesperancinha.logistics.sensor.collector.data.BridgeLogDto;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@ConditionalOnProperty(name = "bridge.logistics.bridge.sensor.active",
    matchIfMissing = true)
public class BridgeSensorReceiver {

    private static Gson gson = new Gson();

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        BridgeLogDto bridgeLogDto = gson.fromJson(messageString, BridgeLogDto.class);
        System.out.println("Received <" + messageString + ">");
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}