package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "merchandise")
public record Merchandise(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    Product product,
    Long quantity,
    Long firstLogTimestamnp,
    Long timestamp) {
}
