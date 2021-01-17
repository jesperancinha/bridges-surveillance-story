package org.jesperancinha.logistics.web.data;

import java.math.BigDecimal;

public record LogMessageDto(Long id,
    String source,
    String type,
    Long timestamp,
    BigDecimal lat,
    BigDecimal lon) {
}
