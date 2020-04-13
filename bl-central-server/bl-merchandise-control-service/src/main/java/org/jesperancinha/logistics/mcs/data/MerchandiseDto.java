package org.jesperancinha.logistics.mcs.data;

public record MerchandiseDto(String id,
    Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId,
    Long timeStamp,
    Long barCode) {
    public MerchandiseDto() {
        this(null, null, null, null, null, null, null, null, null);
    }

}
