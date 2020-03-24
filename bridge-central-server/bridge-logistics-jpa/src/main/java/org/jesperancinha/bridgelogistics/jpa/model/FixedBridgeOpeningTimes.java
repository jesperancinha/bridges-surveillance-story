package org.jesperancinha.bridgelogistics.jpa.model;

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

@Entity
@Table(name = "FIXED_BRIDGE_OPENING_TIMES")
public record FixedBridgeOpeningTimes(

        @Id
        @Column(name = "FixedBridgeOpeningTimeID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        @ManyToOne(cascade = ALL, optional = false)
        @JoinColumn(name = "BridgeID", nullable = false, updatable = false)
        Bridge bridge,

        @Temporal(javax.persistence.TemporalType.DATE)
        @Past
        @Column(name = "OpeningTime")
        Date openingTime,

        @Temporal(javax.persistence.TemporalType.DATE)
        @Past
        @Column(name = "ClosingTime")
        Date closingTime
) {
    public FixedBridgeOpeningTimes() {
        this(null, null, null, null);
    }
}

