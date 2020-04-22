package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "carriage")
public record Carriage(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    String type,
    String brand,
    Long capacity,
    Long axleLoad,
    Long tare,
    Long volume,
    String unitWeight,
    String unitVolume ) {
}
