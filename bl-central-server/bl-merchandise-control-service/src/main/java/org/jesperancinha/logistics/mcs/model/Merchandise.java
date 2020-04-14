package org.jesperancinha.logistics.mcs.model;

import lombok.Builder;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public record Merchandise(@Id @NonNull Long id,
    @NonNull Long supplierId,
    @NonNull Long productId,
    @NonNull Long quantity,
    @NonNull Long volumePerUnit,
    @NonNull Long sourceLocationId,
    @NonNull Long destinationLocationId,
    @NonNull Long timeStamp,
    @NonNull String barCode) {
    @Builder
    public Merchandise {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("A transaction must be supplied!");
        }
        if (id > 0 && (Objects.isNull(supplierId) || supplierId < 0)) {
            throw new IllegalArgumentException("A supplier must be supplied!");
        }
        if (id > 0 && (Objects.isNull(productId) || productId < 0)) {
            throw new IllegalArgumentException("A product must be supplied!");
        }
    }

    public Merchandise() {
        this(-1L, -1L, -1L, -1L, -1L, -1L, -1L, -1L, "");
    }

}
