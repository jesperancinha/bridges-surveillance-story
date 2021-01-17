package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

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
@Data
@Table(name = "bridge_opening_conflicts")
public class BridgeOpeningConflict {
    @Id
    private Long id;
    private String message;
    @ManyToMany(cascade = ALL)
    @JoinColumn(name = "bridge_id", nullable = false, updatable = false)
    private List<BridgeOpeningTime> relatedOpeningTime;
}
