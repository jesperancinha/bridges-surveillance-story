package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Data
@Table(name = "bridge_opening_times")
public class BridgeOpeningTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "bridge_id",
        nullable = false,
        updatable = false)
    private Bridge bridge;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "opening_time")
    private Date openingTime;

    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "closing_time")
    private Date closingTime;
}
