package org.jesperancinha.logistics.sensor.collector.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record SupplierDto(@GeneratedValue(strategy = GenerationType.IDENTITY) @Id Long id,
    String name,
    String address,
    String city,
    String postCode) {
}
