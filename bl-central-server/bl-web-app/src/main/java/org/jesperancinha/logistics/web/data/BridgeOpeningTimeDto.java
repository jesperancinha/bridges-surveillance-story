package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
public record BridgeOpeningTimeDto(BridgeDto bridge,
                                   LocalDateTime openingTime,
                                   LocalDateTime closingTime) {
    @Builder
    public BridgeOpeningTimeDto {
    }
}
