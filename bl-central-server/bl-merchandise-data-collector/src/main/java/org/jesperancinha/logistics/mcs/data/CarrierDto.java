package org.jesperancinha.logistics.mcs.data;

import java.util.List;

public record CarrierDto(Long carrierId,
    Long packageId,
    List<ProductInTransitDto>products) {
}
