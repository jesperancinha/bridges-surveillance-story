package org.jesperancinha.logistics.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.repositories.BridgeCarriageRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeContainerRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeFreightRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeProductRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeTrainRepository;
import org.jesperancinha.logistics.jpa.repositories.BridgeVehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@Profile({ "local", "demo", "prod" })
public class BridgeLogisticsInitializer implements CommandLineRunner {

    private final BridgeRepository bridgeRepository;

    private final BridgeCarriageRepository carriageRepository;

    private final BridgeContainerRepository containerRepository;

    private final BridgeFreightRepository freightRepository;

    private final BridgeProductRepository productRepository;

    private final BridgeTrainRepository trainRepository;

    private final BridgeVehicleRepository vehicleRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BridgeLogisticsInitializer(BridgeRepository bridgeRepository, BridgeCarriageRepository carriageRepository, BridgeContainerRepository containerRepository, BridgeFreightRepository freightRepository, BridgeProductRepository productRepository,
        BridgeTrainRepository trainRepository, BridgeVehicleRepository vehicleRepository) {
        this.bridgeRepository = bridgeRepository;
        this.carriageRepository = carriageRepository;
        this.containerRepository = containerRepository;
        this.freightRepository = freightRepository;
        this.productRepository = productRepository;
        this.trainRepository = trainRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(objectMapper.readValue(getClass().getResourceAsStream("/bridges.json"), Bridge[].class))
            .forEach(bridgeRepository::save);

    }

}