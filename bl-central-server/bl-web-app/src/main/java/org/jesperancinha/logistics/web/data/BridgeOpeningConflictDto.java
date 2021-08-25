package org.jesperancinha.logistics.web.data;

import lombok.Builder;

import java.util.List;

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
public record BridgeOpeningConflictDto(String message,
                                       List<BridgeOpeningTimeDto> relatedOpeningTimes) {
    @Builder
    public BridgeOpeningConflictDto {
    }
}
