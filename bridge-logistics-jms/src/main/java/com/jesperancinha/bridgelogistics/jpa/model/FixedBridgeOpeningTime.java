package com.jesperancinha.bridgelogistics.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

@Getter
@Setter
@Entity(name = "FixedBridgeOpeningTime")
public class FixedBridgeOpeningTime {

    @Id
    @Column(name = "FixedBridgeOpeningTimeID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = ALL, optional = false)
    @JoinColumn(name = "BridgeID", nullable = false, updatable = false)
    private Bridge bridge;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    @Column(name = "OpeningTime")
    private Date openingTime;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Past
    @Column(name = "ClosingTime")
    private Date closingTime;
}
