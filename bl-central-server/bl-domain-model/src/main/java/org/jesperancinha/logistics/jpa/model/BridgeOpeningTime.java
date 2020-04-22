package org.jesperancinha.logistics.jpa.model;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Past;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Table(name = "bridge_opening_times")
public record BridgeOpeningTime(@Id @Column @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    @ManyToOne(cascade = ALL,
        optional = false) @JoinColumn(name = "bridgeId",
        nullable = false,
        updatable = false)
    Bridge bridge,

    @Temporal(javax.persistence.TemporalType.DATE) @Past @Column(name = "opening_time")
    Date openingTime,

    @Temporal(javax.persistence.TemporalType.DATE) @Past @Column(name = "closing_time")
    Date closingTime) {
    @Builder
    public BridgeOpeningTime {
    }
}
