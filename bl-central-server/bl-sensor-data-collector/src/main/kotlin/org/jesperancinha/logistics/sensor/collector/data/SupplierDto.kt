package org.jesperancinha.logistics.sensor.collector.data

data class SupplierDto(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val postCode: String
)