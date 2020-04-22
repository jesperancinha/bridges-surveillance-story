package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String packaging;
    private String brand;
    private Long packageSize;
    private Long weightPerUnit;
    private String unitWeight;
    private String barcode;
    private Long correctionFactor;
    private Long width;
    private Long height;
    private Long depth;
    private String unitDimension;
}
