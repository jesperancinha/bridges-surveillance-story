package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BridgeOpeningTime that = (BridgeOpeningTime) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOpeningTime(), getClosingTime(), getBridge());
    }
}
