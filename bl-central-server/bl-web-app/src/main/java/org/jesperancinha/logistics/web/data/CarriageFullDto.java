package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.util.List;

public record CarriageFullDto(Long carriageId,
    Long people,
    List<ProductDto>products) {
    @Builder
    public CarriageFullDto {
    }
}
