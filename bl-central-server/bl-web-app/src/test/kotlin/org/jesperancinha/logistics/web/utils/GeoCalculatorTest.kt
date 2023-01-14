package org.jesperancinha.logistics.web.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor

class GeoCalculatorTest {
    private val geoCalculator = GeoCalculator(6371)
    @Test
    fun distance() {
        val distance =
            geoCalculator.distance(53.32055555555556, -1.7297222222222222, 53.31861111111111, -1.6997222222222223)
        Assertions.assertThat(distance).isEqualTo(2.0043678382716137)
    }

    @Test
    fun calculateSquareBoundary() {
        val lat1 = BigDecimal.valueOf(53.32055555555556)
        val lon1 = BigDecimal.valueOf(-1.7297222222222222)
        val (westLatitude, eastLatitude, northLongitude, southLongitude) = geoCalculator.calculateSquareBoundary(
            lat1,
            lon1,
            BigDecimal.valueOf(10.0)
        )
        val dLatLeft = floor(
            geoCalculator.distance(
                lat1.toDouble(), lon1.toDouble(), eastLatitude
                    .toDouble(), lon1.toDouble()
            )
        )
        val dLatRight = ceil(
            geoCalculator.distance(
                lat1.toDouble(), lon1.toDouble(), westLatitude
                    .toDouble(), lon1.toDouble()
            )
        )
        val dLatTop = ceil(
            geoCalculator.distance(
                lat1.toDouble(), lon1.toDouble(), lat1.toDouble(), northLongitude
                    .toDouble()
            )
        )
        val dLatBottom = ceil(
            geoCalculator.distance(
                lat1.toDouble(), lon1.toDouble(), lat1.toDouble(), southLongitude
                    .toDouble()
            )
        )
        Assertions.assertThat(dLatLeft).isEqualTo(10.0)
        Assertions.assertThat(dLatRight).isEqualTo(10.0)
        Assertions.assertThat(dLatTop).isEqualTo(10.0)
        Assertions.assertThat(dLatBottom).isEqualTo(10.0)
    }
}