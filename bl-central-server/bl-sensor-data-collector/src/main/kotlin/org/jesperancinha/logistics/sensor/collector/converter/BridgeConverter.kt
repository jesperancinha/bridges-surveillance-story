package org.jesperancinha.logistics.sensor.collector.converter

import org.jesperancinha.logistics.jpa.dao.*
import org.jesperancinha.logistics.sensor.collector.data.BridgeLogDto

object BridgeConverter {
    fun toModel(bridgeLogDto: BridgeLogDto, bridge: Bridge?): BridgeLog {
        return BridgeLog(
            checkInOut = bridgeLogDto.type,
            timestamp = bridgeLogDto.timestamp,
            bridge = bridge
        )
    }
}