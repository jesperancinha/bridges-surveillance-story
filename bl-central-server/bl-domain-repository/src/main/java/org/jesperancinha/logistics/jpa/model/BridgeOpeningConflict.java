package org.jesperancinha.logistics.jpa.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "bridge_opening_conflicts")
public class BridgeOpeningConflict {
    @Id
    private Long id;
    private String message;
    @ManyToMany(cascade = ALL)
    @JoinColumn(name = "bridge_id", nullable = false, updatable = false)
    @ToString.Exclude
    private List<BridgeOpeningTime> relatedOpeningTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BridgeOpeningConflict that = (BridgeOpeningConflict) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessage(), getRelatedOpeningTime());
    }
}
