package org.jesperancinha.logistics.sensor.collector.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.repositories.BridgeVehicleLogRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeVehicleRepository;
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

    private final BridgeVehicleLogRepository bridgeVehicleLogRepository;

    private final BridgeVehicleRepository bridgeVehicleRepository;

    private final CountDownLatch latch = new CountDownLatch(1);

    public VehicleSensorReceiver(Gson gson, BridgeVehicleLogRepository bridgeVehicleLogRepository, BridgeVehicleRepository bridgeVehicleRepository) {
        this.gson = gson;
        this.bridgeVehicleLogRepository = bridgeVehicleLogRepository;
        this.bridgeVehicleRepository = bridgeVehicleRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        VehicleLogDto vehicleLogDto = gson.fromJson(messageString, VehicleLogDto.class);

        if (Objects.nonNull(vehicleLogDto.id())) {
            bridgeVehicleLogRepository.save(VehicleConverter.toModel(vehicleLogDto, bridgeVehicleRepository.findById(vehicleLogDto.id())
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