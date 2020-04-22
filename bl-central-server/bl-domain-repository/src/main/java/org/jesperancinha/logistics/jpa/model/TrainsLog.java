package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "trains_log")
public class TrainsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = ALL,
        optional = false)
    @JoinColumn(name = "trainId",
        nullable = false,
        updatable = false)
    private Train train;
    private Long lat;
    private Long lon;
    private Long timestamp;
}
