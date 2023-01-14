package org.jesperancinha.logistics.jpa.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bridge_logs")
public class BridgeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "bridgeId",
            nullable = false)
    private Bridge bridge;
    private Long timestamp;
    private String checkInOut;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BridgeLog bridgeLog = (BridgeLog) o;

        return Objects.equals(id, bridgeLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBridge(), getTimestamp(), getCheckInOut());
    }
}
