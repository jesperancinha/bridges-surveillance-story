package org.jesperancinha.logistics.sensor.collector.data;

import java.math.BigDecimal;

public record TrainLogDto(Long id,
    String source,
    String type,
    Long timestamp,
    BigDecimal lat,
    BigDecimal lon,
    Long weight,
    Long carriageId) {
}
