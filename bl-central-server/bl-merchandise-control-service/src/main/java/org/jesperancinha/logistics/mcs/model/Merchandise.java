package org.jesperancinha.logistics.mcs.model;

import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public record Merchandise(@Id Long id,
    Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId,
    Long timeStamp,
    Long barCode) {
    @Builder
    public Merchandise {
        if (id > 0 && (Objects.isNull(supplierId) || supplierId < 0)) {
            throw new IllegalArgumentException("A supplier must be supplied!");
        }
    }

    public Merchandise() {
        this(-1L, null, null, null, null, null, null, null, null);
    }

}
