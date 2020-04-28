package org.jesperancinha.logistics.mcs.converter;

import org.jesperancinha.logistics.jpa.model.Company;
import org.jesperancinha.logistics.jpa.model.Merchandise;
import org.jesperancinha.logistics.jpa.model.MerchandiseLog;
import org.jesperancinha.logistics.jpa.model.Product;
import org.jesperancinha.logistics.jpa.model.ProductCargo;
import org.jesperancinha.logistics.jpa.model.TransportPackage;
import org.jesperancinha.logistics.mcs.data.CoontainerDto;

import java.time.Instant;

public class MerchandiseLogConverter {
    public static MerchandiseLog toModel(Company supplier, Company vendor, TransportPackage transportPackage, ProductCargo productCargo) {
        return MerchandiseLog.builder()
            .supplier(supplier)
            .vendor(vendor)
            .timestamp(Instant.now()
                .toEpochMilli())
            .transportPackage(transportPackage)
            .productCargo(productCargo)
            .build();
    }

}