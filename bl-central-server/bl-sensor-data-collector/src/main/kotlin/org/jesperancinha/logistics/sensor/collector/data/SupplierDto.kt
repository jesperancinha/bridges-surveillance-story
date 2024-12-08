package org.jesperancinha.logistics.sensor.collector.data

import java.util.UUID

data class SupplierDto(
    val id: UUID,
    val name: String,
    val address: String,
    val city: String,
    val postCode: String
)