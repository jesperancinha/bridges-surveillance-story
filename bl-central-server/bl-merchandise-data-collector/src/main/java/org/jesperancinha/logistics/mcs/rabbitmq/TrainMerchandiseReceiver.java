package org.jesperancinha.logistics.mcs.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.model.Company;
import org.jesperancinha.logistics.jpa.model.MerchandiseLog;
import org.jesperancinha.logistics.jpa.model.Product;
import org.jesperancinha.logistics.jpa.model.ProductCargo;
import org.jesperancinha.logistics.jpa.model.TransportPackage;
import org.jesperancinha.logistics.jpa.repositories.CarriageRepository;
import org.jesperancinha.logistics.jpa.repositories.CompanyRepository;
import org.jesperancinha.logistics.jpa.repositories.ContainerRepository;
import org.jesperancinha.logistics.jpa.repositories.MerchandiseLogRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductCargoRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductRepository;
import org.jesperancinha.logistics.jpa.repositories.TransportPackageRepository;
import org.jesperancinha.logistics.mcs.data.TrainMerchandiseDto;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@Slf4j
@Component
public class TrainMerchandiseReceiver {

    private final Gson gson;

    private CountDownLatch latch = new CountDownLatch(1);

    private final MerchandiseLogRepository merchandiseLogRepository;

    private final ProductRepository productRepository;

    private final TransportPackageRepository transportPackageRepository;

    private final CompanyRepository companyRepository;

    private final ProductCargoRepository productCargoRepository;

    private final CarriageRepository carriageRepository;

    public TrainMerchandiseReceiver(Gson gson, MerchandiseLogRepository merchandiseLogRepository, ProductRepository productRepository, TransportPackageRepository transportPackageRepository, CompanyRepository companyRepository,
        ProductCargoRepository productCargoRepository, CarriageRepository carriageRepository) {
        this.gson = gson;
        this.merchandiseLogRepository = merchandiseLogRepository;
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
            final TrainMerchandiseDto[] trainMerchandiseDtos = gson.fromJson(messageString, TrainMerchandiseDto[].class);
            Stream.of(trainMerchandiseDtos)
                .forEach(trainMerchandiseDto -> {
                    final Company supplier = companyRepository.findById(trainMerchandiseDto.supplierId())
                        .orElse(null);
                    final Company vendor = companyRepository.findById(trainMerchandiseDto.vendorId())
                        .orElse(null);

                    trainMerchandiseDto.composition()
                        .parallelStream()
                        .filter(carrierDto -> Objects.nonNull(carrierDto.packageId()))
                        .forEach(carrierDto -> {
                            final Long packageId = carrierDto.packageId();
                            TransportPackage transportPackage = TransportPackage.builder()
                                .id(packageId)
                                .supplier(supplier)
                                .vendor(vendor)
                                .carriage(carriageRepository.findById(carrierDto.carriageId())
                                    .orElse(null))
                                .build();
                            TransportPackage transportPackage1 = transportPackageRepository.save(transportPackage);

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
                                    final MerchandiseLog merchandise = MerchandiseLog.builder()
                                        .supplier(supplier)
                                        .vendor(vendor)
                                        .timestamp(Instant.now()
                                            .toEpochMilli())
                                        .transportPackage(transportPackage1)
                                        .productCargo(productCargoDb)
                                        .build();
                                    merchandiseLogRepository.save(merchandise);
                                });

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