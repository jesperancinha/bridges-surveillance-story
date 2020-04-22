package org.jesperancinha.logistics.jpa.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public record Vehicle(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    String brand,
    String model,
    Long weight,
    String unitWeight) {
}
