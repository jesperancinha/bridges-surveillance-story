package org.jesperancinha.logistics.mcs.data;

import lombok.Builder;
import org.jesperancinha.logistics.jpa.types.Status;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.util.List;

public record VehicleMerchandiseDto(@Id @NonNull Long id,
    @NonNull String name,
    Status status,
    @NonNull Long supplierId,
    @NonNull Long vendorId,
    @NonNull List<CoontainerDto>composition) {
    @Builder
    public VehicleMerchandiseDto {
    }

    public VehicleMerchandiseDto() {
        this(null, null, null, null, null, null);
    }

}
