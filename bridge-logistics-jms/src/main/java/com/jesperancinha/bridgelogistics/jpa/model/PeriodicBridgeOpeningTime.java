package com.jesperancinha.bridgelogistics.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "PeriodicBridgeOpeningTime")
public class PeriodicBridgeOpeningTime {

    @Id
    @Column(name="PeriodicBridgeOpeningTimeID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = ALL, optional = false)
    @JoinColumn(name = "BridgeID", nullable = false, updatable = false)
    private Bridge bridge;

    @Column(name = "CronOpeningTime")
    private String cronOpeningTime;

    @Column(name = "CronClosingTime")
    private String cronClosingTime;
}
