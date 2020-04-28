package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.repositories.BridgeTrainRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeTrainsLogRepository;
import org.jesperancinha.logistics.sensor.collector.converter.TrainConverter;
import org.jesperancinha.logistics.sensor.collector.data.TrainLogDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@ConditionalOnProperty(name = "bridge.logistics.train.sensor.active",
    matchIfMissing = true)
public class TrainSensorReceiver {

    private final Gson gson;

    private final BridgeTrainsLogRepository bridgeTrainsLogRepository;

    private final BridgeTrainRepository bridgeTrainRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public TrainSensorReceiver(Gson gson, BridgeTrainsLogRepository bridgeTrainsLogRepository, BridgeTrainRepository bridgeTrainRepository) {
        this.gson = gson;
        this.bridgeTrainsLogRepository = bridgeTrainsLogRepository;
        this.bridgeTrainRepository = bridgeTrainRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        TrainLogDto trainLogDto = gson.fromJson(messageString, TrainLogDto.class);
        if (Objects.nonNull(trainLogDto.id())) {
            bridgeTrainsLogRepository.save(TrainConverter.toModel(trainLogDto, bridgeTrainRepository.findById(trainLogDto.id())
                .orElse(null)));
            System.out.println("Received <" + messageString + ">");
        } else {
            System.out.println("Received <" + messageString + ">");
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}