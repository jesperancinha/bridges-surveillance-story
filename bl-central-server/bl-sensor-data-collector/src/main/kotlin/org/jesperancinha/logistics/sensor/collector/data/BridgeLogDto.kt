package org.jesperancinha.logistics.sensor.collector.data

import java.math.BigDecimal

class BridgeLogDto(val id: Long, source: String, type: String, timestamp: Long, lat: BigDecimal, lon: BigDecimal) {
    val source: String
    val type: String
    val timestamp: Long
    val lat: BigDecimal
    val lon: BigDecimal

    init {
        this.name = name
        this.address = address
        this.city = city
        this.postCode = postCode
        id = id
        this.source = source
        this.type = type
        this.timestamp = timestamp
        this.lat = lat
        this.lon = lon
        this.weight = weight
        this.carriageId = carriageId
        id = id
        this.source = source
        this.type = type
        this.timestamp = timestamp
        this.lat = lat
        this.lon = lon
    }
}