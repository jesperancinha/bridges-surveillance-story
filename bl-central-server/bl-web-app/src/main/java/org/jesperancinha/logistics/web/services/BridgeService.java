package org.jesperancinha.logistics.web.services;

import java.math.BigDecimal;

public interface BridgeService {
    boolean isInArea(final BigDecimal lat, final BigDecimal lon);
}
