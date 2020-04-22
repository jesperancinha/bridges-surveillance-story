package org.jesperancinha.logistics.jpa.model;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
@Entity
@Table(name = "bridge_opening_conflicts")
public record BridgeOpeningConflict(@Id Long id,
    String message,
    @ManyToMany(cascade = ALL) @JoinColumn(name = "bridge_id",
        nullable = false,
        updatable = false)
    List<BridgeOpeningTime>relatedOpeningTime) {
    @Builder
    public BridgeOpeningConflict {
    }

}
