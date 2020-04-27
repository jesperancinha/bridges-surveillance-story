package org.jesperancinha.logistics.sensor.collector.data;

import java.math.BigDecimal;

public record BridgeLogDto(Long id,
    String source,
    String type,
    Long timestamp,
    BigDecimal lat,
    BigDecimal lon) {
}
