package org.jesperancinha.logistics.mcs.rabbitmq;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.logistics.jpa.model.Company;
import org.jesperancinha.logistics.jpa.model.MerchandiseLog;
import org.jesperancinha.logistics.jpa.model.Product;
import org.jesperancinha.logistics.jpa.model.ProductCargo;
import org.jesperancinha.logistics.jpa.model.TransportPackage;
import org.jesperancinha.logistics.jpa.repositories.CompanyRepository;
import org.jesperancinha.logistics.jpa.repositories.MerchandiseLogRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductCargoRepository;
import org.jesperancinha.logistics.jpa.repositories.ProductRepository;
import org.jesperancinha.logistics.jpa.repositories.TransportPackageRepository;
import org.jesperancinha.logistics.mcs.converter.MerchandiseLogConverter;
import org.jesperancinha.logistics.mcs.data.TrainMerchandiseDto;
import org.jesperancinha.logistics.mcs.data.VehicleMerchandiseDto;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

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

    public TrainMerchandiseReceiver(Gson gson, MerchandiseLogRepository merchandiseLogRepository, ProductRepository productRepository, TransportPackageRepository transportPackageRepository, CompanyRepository companyRepository, ProductCargoRepository productCargoRepository) {
        this.gson = gson;
        this.merchandiseLogRepository = merchandiseLogRepository;
        this.productRepository = productRepository;
        this.transportPackageRepository = transportPackageRepository;
        this.companyRepository = companyRepository;
        this.productCargoRepository = productCargoRepository;
    }

    public void receiveMessage(byte[] message) {
        String messageString = new String(message, Charset.defaultCharset());
        System.out.println("Received <" + messageString + ">");
        try {
            final TrainMerchandiseDto trainMerchandiseDto = gson.fromJson(messageString, TrainMerchandiseDto.class);
            final Company supplier = companyRepository.findById(trainMerchandiseDto.supplierId())
                .orElse(null);
            final Company vendor = companyRepository.findById(trainMerchandiseDto.vendorId())
                .orElse(null);

            trainMerchandiseDto.composition()
                .parallelStream()
                .forEach(container -> {
                    final Long packageId = container.packageId();
                    final TransportPackage transportPackage = transportPackageRepository.findById(packageId)
                        .orElse(null);
                    container.products()
                        .parallelStream()
                        .forEach(productInTransitDto -> {
                            final Product product = productRepository.findById(productInTransitDto.productId())
                                .orElse(null);
                            final ProductCargo productCargo = ProductCargo.builder()
                                .product(product)
                                .quantity(productInTransitDto.quantity())
                                .build();
                            final ProductCargo productCargoDb = productCargoRepository.save(productCargo);
                            final MerchandiseLog merchandise = MerchandiseLogConverter.toModel(supplier, vendor, transportPackage, productCargoDb);
                            merchandiseLogRepository.save(merchandise);
                        });

                });
            latch.countDown();
        } catch (Exception e) {
            log.error("Error receiving message!", e);
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}