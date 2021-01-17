package org.jesperancinha.logistics.web.data;

import lombok.Builder;

public record ProductDto(Long productId,
    Long quantity) {
    @Builder
    public ProductDto {
    }
}
