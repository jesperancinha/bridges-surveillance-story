package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
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

    private final Gson gson;

    private final VehicleLogRepository vehicleLogRepository;

    private final VehicleRepository vehicleRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public VehicleSensorReceiver(Gson gson, VehicleLogRepository vehicleLogRepository, VehicleRepository vehicleRepository) {
        this.gson = gson;
        this.vehicleLogRepository = vehicleLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        VehicleLogDto vehicleLogDto = gson.fromJson(messageString, VehicleLogDto.class);

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