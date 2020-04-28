package org.jesperancinha.logistics.mcs.data;

import lombok.Builder;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.util.List;

public record TrainMerchandiseDto(@Id @NonNull Long id,
    @NonNull String name,
    @NonNull Long supplierId,
    String type,
    @NonNull Long vendorId,
    @NonNull List<CarrierDto>composition) {
    @Builder
    public TrainMerchandiseDto {
    }
    public TrainMerchandiseDto() {
        this(null, null, null, null, null, null);
    }

}
