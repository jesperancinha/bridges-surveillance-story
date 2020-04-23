package org.jesperancinha.logistics.web.services;

import java.math.BigDecimal;

public interface BridgeOpeningService {
    boolean isBridgeOpen(BigDecimal lat, BigDecimal lon);
}
