package org.jesperancinha.logistics.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carriage")
public class Carriage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String type;
    private String brand;
    private Long capacity;
    private Long passengers;
    private Long axleLoad;
    private Long tare;
    private Long volume;
    private String unitWeight;
    private String unitVolume;
}
