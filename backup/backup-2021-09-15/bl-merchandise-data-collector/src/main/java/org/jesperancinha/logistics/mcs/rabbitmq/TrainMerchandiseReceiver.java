package org.jesperancinha.logistics.mcs.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.dao.Company;
import org.jesperancinha.logistics.jpa.dao.MerchandiseLog;
import org.jesperancinha.logistics.jpa.dao.Product;
import org.jesperancinha.logistics.jpa.dao.ProductCargo;
import org.jesperancinha.logistics.jpa.dao.TransportPackage;
import org.jesperancinha.logistics.jpa.repositories.CarriageRepository;
import org.jesperancinha.logistics.jpa.repositories.CompanyRepository;
import org.jesperancinha.logistics.jpa.repositories.MerchandiseLogRepository;
import org.jesperancinha.logistics.jpa.repositories.MerchandiseRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductCargoRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductRepository;
import org.jesperancinha.logistics.jpa.repositories.TransportPackageRepository;
import org.jesperancinha.logistics.mcs.converter.MerchandiseLogConverter;
import org.jesperancinha.logistics.mcs.dto.TrainMerchandiseDto;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

import static org.jesperancinha.logistics.jpa.types.Status.DELIVERED;

@Slf4j
@Component
public class TrainMerchandiseReceiver {

    private final ObjectMapper objectMapper;

    private CountDownLatch latch = new CountDownLatch(1);

    private final MerchandiseLogRepository merchandiseLogRepository;

    private final MerchandiseRepository merchandiseRepository;

    private final ProductRepository productRepository;

    private final TransportPackageRepository transportPackageRepository;

    private final CompanyRepository companyRepository;

    private final ProductCargoRepository productCargoRepository;

    private final CarriageRepository carriageRepository;

    public TrainMerchandiseReceiver(ObjectMapper objectMapper, MerchandiseLogRepository merchandiseLogRepository, MerchandiseRepository merchandiseRepository, ProductRepository productRepository, TransportPackageRepository transportPackageRepository,
                                    CompanyRepository companyRepository, ProductCargoRepository productCargoRepository, CarriageRepository carriageRepository) {
        this.objectMapper = objectMapper;
        this.merchandiseLogRepository = merchandiseLogRepository;
        this.merchandiseRepository = merchandiseRepository;
        this.productRepository = productRepository;
        this.transportPackageRepository = transportPackageRepository;
        this.companyRepository = companyRepository;
        this.productCargoRepository = productCargoRepository;
        this.carriageRepository = carriageRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        try {
            final TrainMerchandiseDto[] trainMerchandiseDtos = objectMapper.readValue(messageString, TrainMerchandiseDto[].class);
            Stream.of(trainMerchandiseDtos)
                    .forEach(trainMerchandiseDto -> {
                        final Company supplier;
                        if (Objects.nonNull(trainMerchandiseDto.supplierId())) {
                            supplier = companyRepository.findById(trainMerchandiseDto.supplierId())
                                    .orElse(null);
                        } else {
                            supplier = null;
                        }

                        final Company vendor;
                        if (Objects.nonNull(trainMerchandiseDto.vendorId())) {
                            vendor = companyRepository.findById(trainMerchandiseDto.vendorId())
                                    .orElse(null);
                        } else {
                            vendor = null;
                        }

                        trainMerchandiseDto.composition()
                                .parallelStream()
                                .forEach(carrierDto -> {
                                    final Long packageId = carrierDto.packageId();
                                    final TransportPackage transportPackage = TransportPackage.builder()
                                            .id(packageId)
                                            .supplier(supplier)
                                            .vendor(vendor)
                                            .weight(carrierDto.weight())
                                            .carriage(carriageRepository.findById(carrierDto.carriageId())
                                                    .orElse(null))
                                            .productCargos(new ArrayList<>())
                                            .build();
                                    final TransportPackage transportPackage1 = transportPackageRepository.save(transportPackage);
                                    if (Objects.nonNull(carrierDto.products())) {
                                        carrierDto.products()
                                                .parallelStream()
                                                .forEach(productInTransitDto -> {
                                                    final Product product = productRepository.findById(productInTransitDto.productId())
                                                            .orElse(null);
                                                    final ProductCargo productCargo = ProductCargo.builder()
                                                            .product(product)
                                                            .quantity(productInTransitDto.quantity())
                                                            .build();
                                                    final ProductCargo productCargoDb = productCargoRepository.save(productCargo);
                                                    final MerchandiseLog merchandiseLog = MerchandiseLog.builder()
                                                            .supplier(supplier)
                                                            .vendor(vendor)
                                                            .timestamp(Instant.now()
                                                                    .toEpochMilli())
                                                            .transportPackage(transportPackage1)
                                                            .productCargo(productCargoDb)
                                                            .status(trainMerchandiseDto.status())
                                                            .lat(trainMerchandiseDto.lat())
                                                            .lon(trainMerchandiseDto.lon())
                                                            .build();
                                                    transportPackage1.getProductCargos()
                                                            .add(productCargoDb);
                                                    merchandiseLogRepository.save(merchandiseLog);
                                                    if (merchandiseLog.getStatus() == DELIVERED) {
                                                        merchandiseRepository.save(MerchandiseLogConverter.toMerchandise(merchandiseLog));
                                                    }
                                                });

                                    }
                                    transportPackageRepository.save(transportPackage1);

                                });
                        latch.countDown();
                    });
        } catch (Exception e) {
            log.error("Error receiving message!", e);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
