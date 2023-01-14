package org.jesperancinha.logistics.sensor.collector.data

import java.math.BigDecimal

data class TrainLogDto(
    val id: Long,
    val source: String,
    val type: String,
    val timestamp: Long,
    val lat: BigDecimal,
    val lon: BigDecimal,
    val weight: Long,
    val carriageId: Long
)