package org.jesperancinha.logistics.web.services

import java.math.BigDecimal

data class SquareBoundary(
    val westLatitude: BigDecimal,
    val eastLatitude: BigDecimal,
    val northLongitude: BigDecimal,
    val southLongitude: BigDecimal
)