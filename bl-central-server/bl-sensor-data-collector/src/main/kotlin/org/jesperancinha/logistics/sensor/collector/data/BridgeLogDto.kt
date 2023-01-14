package org.jesperancinha.logistics.sensor.collector.data

import java.math.BigDecimal

data class BridgeLogDto(
    val id: Long,
    val source: String,
    val type: String,
    val timestamp: Long,
    val lat: BigDecimal,
    val lon: BigDecimal
)