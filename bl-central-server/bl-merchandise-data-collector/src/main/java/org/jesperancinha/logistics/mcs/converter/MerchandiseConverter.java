package org.jesperancinha.logistics.mcs.converter;

import org.jesperancinha.logistics.mcs.data.MerchandiseDto;
import org.jesperancinha.logistics.mcs.model.Merchandise;

public class MerchandiseConverter {
    public static Merchandise toModel(MerchandiseDto merchandiseDto) {
        return Merchandise.builder()
            .id(merchandiseDto.id())
            .barCode(merchandiseDto.barCode())
            .destinationLocationId(merchandiseDto.destinationLocationId())
            .productId(merchandiseDto.productId())
            .quantity(merchandiseDto.quantity())
            .sourceLocationId(merchandiseDto.sourceLocationId())
            .supplierId(merchandiseDto.supplierId())
            .timeStamp(merchandiseDto.timeStamp())
            .volumePerUnit(merchandiseDto.volumePerUnit())
            .build();
    }
}
