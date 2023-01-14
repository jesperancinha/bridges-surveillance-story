package org.jesperancinha.logistics.web.data

import lombok.Builder

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
data class BridgeOpeningConflictDto(
    val message: String? = null,
    val relatedOpeningTimes: MutableList<BridgeOpeningTimeDto>
)