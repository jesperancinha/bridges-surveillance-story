package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.repositories.VehicleLogRepository;
import org.jesperancinha.logistics.jpa.repositories.VehicleRepository;
import org.jesperancinha.logistics.sensor.collector.converter.VehicleConverter;
import org.jesperancinha.logistics.sensor.collector.data.VehicleLogDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Component
@ConditionalOnProperty(name = "bridge.logistics.vehicle.sensor.active",
        matchIfMissing = true)
public class VehicleSensorReceiver {

    private final ObjectMapper objectMapper;

    private final VehicleLogRepository vehicleLogRepository;

    private final VehicleRepository vehicleRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public VehicleSensorReceiver(final ObjectMapper objectMapper, VehicleLogRepository vehicleLogRepository, VehicleRepository vehicleRepository) {
        this.objectMapper = objectMapper;
        this.vehicleLogRepository = vehicleLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public void receiveMessage(byte[] message) throws JsonProcessingException {
        String messageString = new String(message, Charset.defaultCharset());
        VehicleLogDto vehicleLogDto = objectMapper.readValue(messageString, VehicleLogDto.class);

        if (Objects.nonNull(vehicleLogDto.id())) {
            vehicleLogRepository.save(VehicleConverter.toModel(vehicleLogDto, vehicleRepository.findById(vehicleLogDto.id())
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