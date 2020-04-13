package org.jesperancinha.logistics.mcs.rabbitmq;

import com.google.gson.Gson;
import org.jesperancinha.logistics.mcs.converter.MerchandiseConverter;
import org.jesperancinha.logistics.mcs.data.MerchandiseDto;
import org.jesperancinha.logistics.mcs.model.Merchandise;
import org.jesperancinha.logistics.mcs.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private static Gson gson = new Gson();

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private MerchandiseRepository merchandiseRepository;

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        MerchandiseDto merchandiseDto = gson.fromJson(messageString, MerchandiseDto.class);
        Merchandise merchandise = MerchandiseConverter.toData(merchandiseDto);
        merchandiseRepository.save(merchandise);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}