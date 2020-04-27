package org.jesperancinha.logistics.mcs.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.mcs.converter.MerchandiseConverter;
import org.jesperancinha.logistics.mcs.data.MerchandiseDto;
import org.jesperancinha.logistics.mcs.model.Merchandise;
import org.jesperancinha.logistics.mcs.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
public class Receiver {

    private static Gson gson = new Gson();

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        try {
            MerchandiseDto merchandiseDto = gson.fromJson(messageString, MerchandiseDto.class);
            Merchandise merchandise = MerchandiseConverter.toData(merchandiseDto);
            merchandiseRepository.save(merchandise);
            latch.countDown();
        } catch (Exception e) {
            log.error("Error receiving message!", e);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}