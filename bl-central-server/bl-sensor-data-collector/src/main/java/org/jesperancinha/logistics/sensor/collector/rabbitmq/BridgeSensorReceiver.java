package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.repositories.LogRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeRepository;
import org.jesperancinha.logistics.sensor.collector.converter.BridgeConverter;
import org.jesperancinha.logistics.sensor.collector.data.BridgeLogDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@ConditionalOnProperty(name = "bridge.logistics.bridge.sensor.active",
    matchIfMissing = true)
public class BridgeSensorReceiver {

    private final Gson gson;

    private final LogRepository logRepository;

    private final BridgeRepository bridgeRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public BridgeSensorReceiver(Gson gson, LogRepository logRepository, BridgeRepository bridgeRepository) {
        this.gson = gson;
        this.logRepository = logRepository;
        this.bridgeRepository = bridgeRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        BridgeLogDto bridgeLogDto = gson.fromJson(messageString, BridgeLogDto.class);
        if (Objects.nonNull(bridgeLogDto.id())) {
            Bridge bridge = bridgeRepository.findById(bridgeLogDto.id())
                .orElse(null);
            logRepository.save(BridgeConverter.toModel(bridgeLogDto, bridge));
            System.out.println("Received <" + messageString + ">");
        } else {
            System.out.println("Received empty id message <" + messageString + ">");
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}