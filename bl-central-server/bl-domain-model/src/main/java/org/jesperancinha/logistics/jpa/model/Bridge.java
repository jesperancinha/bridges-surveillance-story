package org.jesperancinha.logistics.jpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "bridge")
public record Bridge(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    @NotNull String name,
    String address,
    String city,
    String postCode,
    String country,
    Long lat,
    Long lon) {
    public Bridge() {
        this(null, null, null, null, null, null, null, null);
    }
}
