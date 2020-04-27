package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@Profile("bridge")
public class BridgeSensorReceiver {

    private static Gson gson = new Gson();

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}