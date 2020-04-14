package org.jesperancinha.logistics.mcs.data;

import lombok.Builder;
import org.springframework.lang.NonNull;

import javax.persistence.Id;

public record MerchandiseDto(@Id @NonNull Long id,
    @NonNull Long supplierId,
    @NonNull Long productId,
    @NonNull Long quantity,
    @NonNull Long volumePerUnit,
    @NonNull Long sourceLocationId,
    @NonNull Long destinationLocationId,
    @NonNull Long timeStamp,
    @NonNull String barCode) {
    @Builder
    public MerchandiseDto {
    }

    public MerchandiseDto() {
        this(null, null, null, null, null, null, null, null, null);
    }

}
