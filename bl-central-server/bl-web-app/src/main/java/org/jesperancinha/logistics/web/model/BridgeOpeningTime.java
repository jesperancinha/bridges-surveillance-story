package org.jesperancinha.logistics.web.model;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Table(name = "bridge_opening_times")
public record BridgeOpeningTime(Bridge bridge,
    LocalDateTime openingTime,
    LocalDateTime closingTime) {
    @Builder
    public BridgeOpeningTime {
    }
}
