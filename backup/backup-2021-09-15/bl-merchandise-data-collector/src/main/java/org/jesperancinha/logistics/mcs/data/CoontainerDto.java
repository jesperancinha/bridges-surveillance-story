package org.jesperancinha.logistics.mcs.dto;

import java.util.List;

public record CoontainerDto(Long containerId,
    Long packageId,
    List<ProductInTransitDto>products) {
}
