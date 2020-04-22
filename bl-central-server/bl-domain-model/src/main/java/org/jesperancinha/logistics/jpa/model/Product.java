package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public record Product(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    String name,
    String packaging,
    String brand,
    Long packageSize,
    Long weightPerUnit,
    String unitWeight,
    String barcode,
    Long correctionFactor,
    Long width,
    Long height,
    Long depth,
    String unitDimension
) {
}
