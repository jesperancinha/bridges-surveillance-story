package org.jesperancinha.logistics.mcs.converter;

import org.jesperancinha.logistics.jpa.model.Merchandise;
import org.jesperancinha.logistics.jpa.model.MerchandiseLog;

public class MerchandiseLogConverter {

    public static Merchandise toMerchandise(MerchandiseLog merchandiseLog) {
        return Merchandise.builder()
            .productCargo(merchandiseLog.getProductCargo())
            .supplier(merchandiseLog.getSupplier())
            .vendor(merchandiseLog.getVendor())
            .timestamp(merchandiseLog.getTimestamp())
            .transportPackage(merchandiseLog.getTransportPackage())
            .build();
    }
}