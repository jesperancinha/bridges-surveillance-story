package com.jesperancinha.bridgelogistics.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "BRIDGE")
@NamedQueries({
        @NamedQuery(name = "Bridge.findAll",
                query = "SELECT b FROM Bridge b")
})
public record Bridge(
        @Id
        @Column(name = "BridgeID")
        @GeneratedValue(strategy = GenerationType.AUTO)
        Long id,
        @NotNull
        String name,
        String address,
        String city,
        String postCode,
        String country
) {
    public Bridge() {
        this(null, null, null, null, null, null);
    }
}
