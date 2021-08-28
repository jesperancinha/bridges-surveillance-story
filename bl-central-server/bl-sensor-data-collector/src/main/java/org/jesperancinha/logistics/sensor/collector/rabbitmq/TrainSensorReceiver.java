package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.repositories.TrainRepository;
import org.jesperancinha.logistics.jpa.repositories.TrainsLogRepository;
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

    private final ObjectMapper objectMapper;

    private final TrainsLogRepository trainsLogRepository;

    private final TrainRepository trainRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public TrainSensorReceiver(ObjectMapper objectMapper, TrainsLogRepository trainsLogRepository, TrainRepository trainRepository) {
        this.objectMapper = objectMapper;
        this.trainsLogRepository = trainsLogRepository;
        this.trainRepository = trainRepository;
    }

    public void receiveMessage(byte[] message) throws JsonProcessingException {
        final var messageString = new String(message, Charset.defaultCharset());
        final var trainLogDto = objectMapper.readValue(messageString, TrainLogDto.class);
        if (Objects.nonNull(trainLogDto.id())) {
            trainsLogRepository.save(TrainConverter.toModel(trainLogDto, trainRepository.findById(trainLogDto.id())
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