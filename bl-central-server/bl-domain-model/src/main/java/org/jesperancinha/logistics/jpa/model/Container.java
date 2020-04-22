package org.jesperancinha.logistics.jpa.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "container")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String type;
    private String brand;
    private Long capacity;
    private Long axleLoad;
    private Long tare;
    private Long volume;
    private String unitWeight;
    private String unitVolume;
}
