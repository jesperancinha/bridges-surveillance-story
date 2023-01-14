package org.jesperancinha.logistics.mcs.dto;

import lombok.Builder;
import org.jesperancinha.logistics.jpa.types.Status;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

public record VehicleMerchandiseDto(
    @NonNull Long id,
    @NonNull String name,
    Status status,
    @NonNull Long supplierId,
    @NonNull Long vendorId,
    @NonNull List<CoontainerDto>composition,
    BigDecimal lat,
    BigDecimal lon) {
    @Builder
    public VehicleMerchandiseDto {
    }
    public VehicleMerchandiseDto() {
        this(null, null, null, null, null, null, null, null);
    }
}
