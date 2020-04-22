package org.jesperancinha.logistics.web.model;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bridges")
public record Bridge(
    String name,
    String address,
    String city,
    String postCode,
    String country) {

    @Builder
    public Bridge {
    }

    public Bridge() {
        this(null, null, null, null, null);
    }
}
