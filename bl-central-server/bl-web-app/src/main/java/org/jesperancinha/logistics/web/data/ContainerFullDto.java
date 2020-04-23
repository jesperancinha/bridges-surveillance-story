package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.util.List;

public record ContainerFullDto(Long containerId,
    List<ProductDto>products) {
    @Builder
    public ContainerFullDto {
    }
}
