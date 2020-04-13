package org.jesperancinha.logistics.mcs.data;

import lombok.Builder;

public record MerchandiseDto(Long id,
    Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId,
    Long timeStamp,
    Long barCode) {
    @Builder
    public MerchandiseDto{

    }
    public MerchandiseDto() {
        this(null, null, null, null, null, null, null, null, null);
    }

}
