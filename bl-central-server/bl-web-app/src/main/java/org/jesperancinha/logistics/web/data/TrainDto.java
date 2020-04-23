package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.util.List;

public record TrainDto(Long id,
    String name,
    String type,
    List<CarriageFullDto>composition) {
    @Builder
    public TrainDto {
    }
}
