package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
@Table(name = "transport_package")
public class TransportPackage {

    @Id
    private Long id;

    @ManyToOne
    private Company supplier;

    @ManyToOne
    private Company vendor;

    @OneToMany
    private List<ProductCargo> productCargos;

    @ManyToOne
    private Carriage carriage;

    @ManyToOne
    private Container container;

}
