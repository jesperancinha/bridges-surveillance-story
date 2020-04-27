package org.jesperancinha.logistics.mcs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record Location(@GeneratedValue(strategy = GenerationType.IDENTITY) @Id Long id,
    String address,
    String postCode,
    String city,
    String country,
    Long longitude,
    Long latitude) {
}
