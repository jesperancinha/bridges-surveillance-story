package org.jesperancinha.logistics.sensor.collector.data

import java.math.BigDecimal
import java.util.UUID

data class TrainLogDto(
    val id: UUID,
    val source: String,
    val type: String,
    val timestamp: Long,
    val lat: BigDecimal,
    val lon: BigDecimal,
    val weight: Long,
    val carriageId: UUID
)