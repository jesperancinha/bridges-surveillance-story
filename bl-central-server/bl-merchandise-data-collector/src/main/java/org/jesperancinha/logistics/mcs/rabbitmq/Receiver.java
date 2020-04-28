package org.jesperancinha.logistics.mcs.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.repositories.BridgeMerchandiseLogRepository;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class Receiver {

    private final Gson gson;

    private CountDownLatch latch = new CountDownLatch(1);

    private final BridgeMerchandiseLogRepository bridgeMerchandiseLogRepository;

    public Receiver(Gson gson, BridgeMerchandiseLogRepository bridgeMerchandiseLogRepository) {
        this.gson = gson;
        this.bridgeMerchandiseLogRepository = bridgeMerchandiseLogRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        try {
//            MerchandiseDto merchandiseDto = gson.fromJson(messageString, MerchandiseDto.class);
//            Merchandise merchandise = MerchandiseConverter.toModel (merchandiseDto);
//            bridgeMerchandiseLogRepository.save(merchandise);
//            latch.countDown();
        } catch (Exception e) {
            log.error("Error receiving message!", e);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}