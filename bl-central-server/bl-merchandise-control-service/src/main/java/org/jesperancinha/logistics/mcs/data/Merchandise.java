package org.jesperancinha.logistics.mcs.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public record Merchandise(@Id  String id,
    Long supplierId,
    Long productId,
    Long quantity,
    Long volumePerUnit,
    Long sourceLocationId,
    Long destinationLocationId) {


}
