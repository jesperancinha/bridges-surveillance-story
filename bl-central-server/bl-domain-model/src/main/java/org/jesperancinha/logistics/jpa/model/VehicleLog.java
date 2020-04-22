package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "vehicles_log")
public class VehicleLog {
    Long id;
    @ManyToOne(cascade = ALL,
        optional = false)
    @JoinColumn(name = "train_id",
        nullable = false,
        updatable = false)
    Vehicle vehicle;
    String transportType;
    Long lat;
    Long lon;
    Long timestamp;
}