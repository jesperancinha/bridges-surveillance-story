package org.jesperancinha.logistics.jpa.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

public record Freight(@Id @GeneratedValue(strategy = GenerationType.AUTO)Long id,
    String name,
    String type,
    @ManyToMany(cascade = ALL) @JoinColumn(name = "container_id",
        nullable = false,
        updatable = false)
    List<Container>containers) {
}
