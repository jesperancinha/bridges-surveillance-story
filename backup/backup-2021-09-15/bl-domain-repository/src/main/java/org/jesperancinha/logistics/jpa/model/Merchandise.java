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
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "merchandise")
public class Merchandise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long timestamp;

    @ManyToOne
    private Company supplier;

    @ManyToOne
    private Company vendor;

    @ManyToOne
    private TransportPackage transportPackage;

    @ManyToOne
    private ProductCargo productCargo;

    @Column(precision = 10, scale = 6)
    private BigDecimal lat;
    @Column(precision = 10, scale = 6)
    private BigDecimal lon;
}
