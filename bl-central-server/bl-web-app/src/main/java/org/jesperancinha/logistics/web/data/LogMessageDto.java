package org.jesperancinha.logistics.web.data;

import java.math.BigDecimal;

public record LogInMessageDto(Long id,
    String type,
    Long checkin,
    BigDecimal lat,
    BigDecimal lon) {
}
