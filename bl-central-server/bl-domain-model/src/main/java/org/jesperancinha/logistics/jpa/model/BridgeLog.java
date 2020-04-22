package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "bridge_logs")
public record BridgeLog(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    @ManyToOne(cascade = ALL,
        optional = false) @JoinColumn(name = "bridgeId",
        nullable = false,
        updatable = false)
    Bridge bridge,
    Long timestamp,
    String checkInOut) {
}
