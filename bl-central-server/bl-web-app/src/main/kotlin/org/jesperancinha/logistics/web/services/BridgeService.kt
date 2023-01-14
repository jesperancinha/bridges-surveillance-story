package org.jesperancinha.logistics.web.services

import org.jesperancinha.logistics.jpa.dao.BridgeRepository
import org.jesperancinha.logistics.web.utils.GeoCalculator
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BridgeService(private val bridgeRepository: BridgeRepository, private val geoCalculator: GeoCalculator) {
    fun isInArea(lat: BigDecimal, lon: BigDecimal): Boolean {
        val (westLatitude, eastLatitude, northLongitude, southLongitude) = geoCalculator.calculateSquareBoundary(
            lat,
            lon,
            BigDecimal.ONE
        )
        val bridgeByLatAndLonUnderRadius =
            bridgeRepository.findBridgeBySquareBoundary(westLatitude, eastLatitude, northLongitude, southLongitude)
        return bridgeByLatAndLonUnderRadius != null && bridgeByLatAndLonUnderRadius.size == 1
    }
}