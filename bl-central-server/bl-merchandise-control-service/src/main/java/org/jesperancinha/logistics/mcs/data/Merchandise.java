package org.jesperancinha.logistics.mcs.data;

public record Merchandise(Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId) {
}
