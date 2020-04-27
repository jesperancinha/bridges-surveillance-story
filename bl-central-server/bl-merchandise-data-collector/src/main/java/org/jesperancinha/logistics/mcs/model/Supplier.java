package org.jesperancinha.logistics.mcs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record Supplier(@GeneratedValue(strategy = GenerationType.IDENTITY) @Id Long id,
    String name,
    String address,
    String city,
    String postCode) {
}
