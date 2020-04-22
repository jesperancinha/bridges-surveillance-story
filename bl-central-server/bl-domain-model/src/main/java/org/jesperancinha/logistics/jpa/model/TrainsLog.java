package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "trains_log")
public record TrainsLog(
    Long id,
    @ManyToOne(cascade = ALL,
        optional = false) @JoinColumn(name = "trainId",
        nullable = false,
        updatable = false)
    Long transportId,
    String trainId,
    Long lat,
    Long lon,
    Long timestamp
) {
}
