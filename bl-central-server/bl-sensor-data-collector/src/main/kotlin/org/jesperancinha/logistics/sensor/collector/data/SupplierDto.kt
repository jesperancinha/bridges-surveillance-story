package org.jesperancinha.logistics.sensor.collector.data

import javax.persistence.*

@Entity
@Table
@JvmRecord
data class SupplierDto(
    @field:Id @field:GeneratedValue(strategy = GenerationType.IDENTITY) @param:GeneratedValue(
        strategy = GenerationType.IDENTITY
    ) @param:Id val id: Long, val name: String, val address: String, val city: String, val postCode: String
)