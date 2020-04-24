package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bridge_opening_times")
@NamedQuery(name = "BridgeOpeningTime.findBridgeBySquareBoundaryUnderRadius",
    query = "select bot from BridgeOpeningTime bot"
        + " where bot.bridge.lat>=:latWest and bot.bridge.lat<=:latEast and bot.bridge.lon<=:lonNorth and bot.bridge.lon>=:lonSouth"
        + " and bot.openingTime <= :milliseconds and bot.closingTime > :milliseconds ")
public class BridgeOpeningTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opening_time")
    private Long openingTime;

    @Column(name = "closing_time")
    private Long closingTime;

    @ManyToOne(optional = false,
        cascade = ALL)
    @JoinColumn(name = "bridge_id",
        nullable = false,
        updatable = false,
        referencedColumnName = "id")
    private Bridge bridge;
}
