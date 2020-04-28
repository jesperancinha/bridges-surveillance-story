package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package")
public class TransportPackage {
    @Id
    private Long id;

    @OneToMany
    private List<ProductCargo> products;

    @ManyToOne
    private Carriage carriage;

    @ManyToOne
    private Container container;

}
