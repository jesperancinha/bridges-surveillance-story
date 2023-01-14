package org.jesperancinha.logistics.web.utils

import org.jesperancinha.logistics.web.services.SquareBoundary
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.math.*

/**
 * https://en.wikipedia.org/wiki/Haversine_formula
 */
@Component
class GeoCalculator(
    @param:Value("\${bridge.logistics.planet.radius:6371}") private val planetRadius: Int
) {
    /**
     * Using the Haversine formula
     * Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val lat1Rad = Math.toRadians(lat1)
        val lon1Rad = Math.toRadians(lon1)
        val lat2Rad = Math.toRadians(lat2)
        val lon2Rad = Math.toRadians(lon2)
        val deltaLon = lon2Rad - lon1Rad
        val deltaLat = lat2Rad - lat1Rad
        return planetRadius * 2 * asin(
            sqrt(
                sin(deltaLat / 2).pow(2.0) + cos(lat1Rad) * cos(lat2Rad) * sin(deltaLon / 2).pow(2.0)
            )
        )
    }

    /**
     * lat = lat0 + (180/pi)*(dy/6378137)
     * lon = lon0 + (180/pi)*(dx/6378137)/cos(lat0)
     * @param lat
     * @param lon
     * @param radius Radius in Km
     * @return
     */
    fun calculateSquareBoundary(lat: BigDecimal, lon: BigDecimal, radius: BigDecimal): SquareBoundary {
        val dLat = BigDecimal.valueOf(180 / Math.PI * (radius.toDouble() / planetRadius))
        val dLon =
            BigDecimal.valueOf(180 / Math.PI * (radius.toDouble() / planetRadius) / cos(Math.toRadians(lat.toDouble())))
        val latWest = lat.subtract(dLat)
        val latEast = lat.add(dLat)
        val lonNorth = lon.add(dLon)
        val lonSouth = lon.subtract(dLon)
        return SquareBoundary(
            westLatitude = latWest,
            eastLatitude = latEast,
            northLongitude = lonNorth,
            southLongitude = lonSouth
        )
    }
}