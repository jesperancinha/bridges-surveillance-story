package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "merchandise_log")
public class MerchandiseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Product product;
    private Long quantity;
    private Long timestamp;
    @ManyToOne
    private Train train;
    @ManyToOne
    private Vehicle vehicle;
}
