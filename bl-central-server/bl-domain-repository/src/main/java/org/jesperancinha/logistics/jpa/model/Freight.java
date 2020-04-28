package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "freight")
public class Freight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToMany
    @JoinTable(name = "freight_container",
        joinColumns = @JoinColumn(name = "freight_id",
            referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "container_id",
            referencedColumnName = "id"))
    private List<Container> containers;
}
