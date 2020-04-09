package org.jesperancinha.logistics.mcs.rabbitmq;

import org.jesperancinha.logistics.mcs.data.Merchandise;
import org.jesperancinha.logistics.mcs.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        Merchandise merchandise = new Merchandise("1L", 1L, 2L, 3L, 4L, 5L, 6L, Instant.now().toEpochMilli());
        merchandiseRepository.save(merchandise);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}