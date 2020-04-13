package org.jesperancinha.logistics.mcs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record Merchandise(@Id String id,
    Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId,
    Long timeStamp,
    Long barCode) {
    @Builder
    public Merchandise{
        if (supplierId < 0) {
            throw new IllegalArgumentException( "A supplier must be supplied!");
        }
    }

}
