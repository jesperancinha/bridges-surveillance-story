package org.jesperancinha.logistics.mcs.dto;

import java.util.List;

public record CarrierDto(Long carriageId,
    Long packageId,
    Long weight,
    List<ProductInTransitDto>products) {
}
