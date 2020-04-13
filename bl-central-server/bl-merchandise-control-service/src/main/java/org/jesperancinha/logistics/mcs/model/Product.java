package org.jesperancinha.logistics.mcs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record Product(@GeneratedValue(strategy = GenerationType.IDENTITY) @Id Long id,
    String name,
    String brand) {
}
