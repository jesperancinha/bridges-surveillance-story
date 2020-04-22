package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "vehicles_log")
public record VehicleLog(
    Long id,
    @ManyToOne(cascade = ALL,
        optional = false) @JoinColumn(name = "train_id",
        nullable = false,
        updatable = false)
    Long vehicleId,
    String transportType,
    Long lat,
    Long lon,
    Long timestamp
) {
}
