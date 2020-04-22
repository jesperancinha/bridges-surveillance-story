package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "trains_log")
public class TrainsLog {
    Long id;
    @ManyToOne(cascade = ALL,
        optional = false)
    @JoinColumn(name = "trainId",
        nullable = false,
        updatable = false)
    private Train train;
    private String trainId;
    private Long lat;
    private Long lon;
    private Long timestamp;
}
