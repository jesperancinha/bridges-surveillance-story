package org.jesperancinha.logistics.mcs.rabbitmq;

import org.jesperancinha.logistics.mcs.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
//        merchandiseRepository.save(merchandise);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}