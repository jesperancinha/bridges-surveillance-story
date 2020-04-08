package org.jesperancinha.logistics.mcs;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}