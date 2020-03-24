package org.jesperancinha.bridgelogistics.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "PERIODIC_BRIDGE_OPENING_TIMES")
public record PeriodicBridgeOpeningTimes(

        @Id
        @Column(name = "PeriodicBridgeOpeningTimeID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,

        @ManyToOne(cascade = ALL, optional = false)
        @JoinColumn(name = "BridgeID", nullable = false, updatable = false)
        Bridge bridge,

        @Column(name = "CronOpeningTime")
        String cronOpeningTime,

        @Column(name = "CronClosingTime")
        String cronClosingTime
) {
    public PeriodicBridgeOpeningTimes() {
        this(null, null, null, null);
    }
}
