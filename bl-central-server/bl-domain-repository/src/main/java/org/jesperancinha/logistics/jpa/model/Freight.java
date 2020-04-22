package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Table(name = "freight")
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToMany(cascade = ALL)
    @JoinColumn(name = "container_id",
        nullable = false,
        updatable = false)
    private List<Container> containers;
}
