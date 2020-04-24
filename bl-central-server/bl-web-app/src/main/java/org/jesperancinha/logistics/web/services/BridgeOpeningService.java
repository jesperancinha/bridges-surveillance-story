package org.jesperancinha.logistics.web.services;

import java.math.BigDecimal;

public interface BridgeOpeningService {
    boolean isBridgeOpen(final BigDecimal lat, final BigDecimal lon);
}
