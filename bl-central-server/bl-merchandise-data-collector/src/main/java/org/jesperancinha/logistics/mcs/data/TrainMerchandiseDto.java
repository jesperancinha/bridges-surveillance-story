package org.jesperancinha.logistics.mcs.data;

import lombok.Builder;
import org.jesperancinha.logistics.jpa.types.Status;
import org.jesperancinha.logistics.jpa.types.TrainType;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

public record TrainMerchandiseDto(@Id @NonNull Long id,
    @NonNull String name,
    @NonNull Long supplierId,
    TrainType type,
    Status status,
    @NonNull Long vendorId,
    @NonNull List<CarrierDto>composition,
    BigDecimal lat,
    BigDecimal lon) {
    @Builder
    public TrainMerchandiseDto {
    }

    public TrainMerchandiseDto() {
        this(null, null, null, null, null, null, null, null, null);
    }

}
