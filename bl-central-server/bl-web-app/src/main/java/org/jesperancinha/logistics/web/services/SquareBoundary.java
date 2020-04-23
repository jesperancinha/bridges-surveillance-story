package org.jesperancinha.logistics.web.services;

import lombok.Builder;

import java.math.BigDecimal;

public record SquareBoundary(BigDecimal westLatitude,
    BigDecimal eastLatitude,
    BigDecimal northLongitude,
    BigDecimal southLongitude) {
    @Builder
    public SquareBoundary{
    }
}
