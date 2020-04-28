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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @ManyToMany
    @JoinTable(name = "train_container",
        joinColumns = @JoinColumn(name = "train_id",
            referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "carriage_id",
            referencedColumnName = "id"))
    private List<Carriage> carriages;

    @ManyToOne
    private Company supplier;

    @ManyToOne
    private Company vendor;
}
