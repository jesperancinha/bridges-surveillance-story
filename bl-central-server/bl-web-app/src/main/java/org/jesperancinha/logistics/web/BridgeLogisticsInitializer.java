package org.jesperancinha.logistics.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.dao.Bridge;
import org.jesperancinha.logistics.jpa.dao.BridgeOpeningTime;
import org.jesperancinha.logistics.jpa.dao.Carriage;
import org.jesperancinha.logistics.jpa.dao.Company;
import org.jesperancinha.logistics.jpa.dao.Container;
import org.jesperancinha.logistics.jpa.dao.Freight;
import org.jesperancinha.logistics.jpa.dao.Passenger;
import org.jesperancinha.logistics.jpa.dao.Product;
import org.jesperancinha.logistics.jpa.dao.Train;
import org.jesperancinha.logistics.jpa.dao.BridgeRepository;
import org.jesperancinha.logistics.jpa.dao.CarriageRepository;
import org.jesperancinha.logistics.jpa.dao.CompanyRepository;
import org.jesperancinha.logistics.jpa.dao.ContainerRepository;
import org.jesperancinha.logistics.jpa.dao.FreightRepository;
import org.jesperancinha.logistics.jpa.dao.OpeningTimeRepository;
import org.jesperancinha.logistics.jpa.dao.PassengerRepository;
import org.jesperancinha.logistics.jpa.dao.ProductRepository;
import org.jesperancinha.logistics.jpa.dao.TrainRepository;
import org.jesperancinha.logistics.web.data.FreightDto;
import org.jesperancinha.logistics.web.data.TrainDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

@Component
@Slf4j
@Profile({"local", "demo"})
public class BridgeLogisticsInitializer implements CommandLineRunner {

    private final BridgeRepository bridgeRepository;

    private final CarriageRepository carriageRepository;

    private final ContainerRepository containerRepository;

    private final FreightRepository freightRepository;

    private final ProductRepository productRepository;

    private final TrainRepository trainRepository;

    private final OpeningTimeRepository openingTimeRepository;

    private final CompanyRepository companyRepository;

    private final PassengerRepository passengerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public BridgeLogisticsInitializer(BridgeRepository bridgeRepository, CarriageRepository carriageRepository, ContainerRepository containerRepository, FreightRepository freightRepository, ProductRepository productRepository, TrainRepository trainRepository,
                                      OpeningTimeRepository openingTimeRepository, CompanyRepository companyRepository, PassengerRepository passengerRepository) {
        this.bridgeRepository = bridgeRepository;
        this.carriageRepository = carriageRepository;
        this.containerRepository = containerRepository;
        this.freightRepository = freightRepository;
        this.productRepository = productRepository;
        this.trainRepository = trainRepository;
        this.openingTimeRepository = openingTimeRepository;
        this.companyRepository = companyRepository;
        this.passengerRepository = passengerRepository;
        JacksonAnnotationIntrospector implicitRecordAI = new JacksonAnnotationIntrospector() {
            @Override
            public String findImplicitPropertyName(AnnotatedMember m) {
                if (m.getDeclaringClass()
                        .isRecord()) {
                    if (m instanceof AnnotatedParameter parameter) {
                        return m.getDeclaringClass()
                                .getRecordComponents()[parameter.getIndex()].getName();
                    }
                }
                return super.findImplicitPropertyName(m);
            }
        };
        objectMapper.setAnnotationIntrospector(implicitRecordAI);
    }

    @Override
    public void run(String... args) throws Exception {
        bridgeRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/bridges.json"), Bridge[].class)));

        companyRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/companies.json"), Company[].class)));

        carriageRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/carriages.json"), Carriage[].class)));

        containerRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/containers.json"), Container[].class)));

        productRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/products.json"), Product[].class)));

        passengerRepository.saveAll(Arrays.asList(objectMapper.readValue(getClass().getResourceAsStream("/passengers/passengers.json"), Passenger[].class)));

        stream(objectMapper.readValue(getClass().getResourceAsStream("/freight.json"), FreightDto[].class)).map(freightDto -> Freight.builder()
                        .id(freightDto.id())
                        .name(freightDto.name())
                        .type(freightDto.type())
                        .containers(freightDto.composition()
                                .stream()
                                .map(containerFullDto -> containerRepository.findById(containerFullDto.containerId())
                                        .orElse(null))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                        .supplier(companyRepository.findById(freightDto.supplierId())
                                .orElse(null))
                        .vendor(companyRepository.findById(freightDto.vendorId())
                                .orElse(null))
                        .build())
                .forEach(freightRepository::save);

        stream(objectMapper.readValue(getClass().getResourceAsStream("/train.json"), TrainDto[].class)).map(trainDto -> Train.builder()
                        .id(trainDto.id())
                        .name(trainDto.name())
                        .type(trainDto.type())
                        .carriages(trainDto.composition()
                                .stream()
                                .map(carriageFullDto -> carriageRepository.findById(carriageFullDto.carriageId())
                                        .orElse(null))
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                        .supplier(companyRepository.findById(trainDto.supplierId())
                                .orElse(null))
                        .vendor(companyRepository.findById(trainDto.vendorId())
                                .orElse(null))
                        .build())
                .forEach(trainRepository::save);

        bridgeRepository.findAll()
                .forEach(bridge -> {
                    final Instant now = Instant.now();
                    final long millisToAdd = 10000;
                    IntStream.range(0, 200)
                            .boxed()
                            .map(integer -> BridgeOpeningTime.builder()
                                    .id((long) integer)
                                    .bridge(bridge)
                                    .openingTime(now.plusMillis(millisToAdd * integer * 2)
                                            .toEpochMilli())
                                    .closingTime(now.plusMillis(millisToAdd * (integer * 2 + 1))
                                            .toEpochMilli())
                                    .build())
                            .forEach(openingTimeRepository::save);
                });

    }

}