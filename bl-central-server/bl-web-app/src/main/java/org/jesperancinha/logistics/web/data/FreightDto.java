package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.util.List;

public record FreightDto(Long id,
    String name,
    String type,
    List<ContainerFullDto>composition) {
    @Builder
    public FreightDto {
    }
}
