package org.jesperancinha.logistics.web.utils;

import org.jesperancinha.logistics.web.services.SquareBoundary;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoCalculatorTest {

    private  GeoCalculator geoCalculator = new GeoCalculator(6371);

    @Test
    public void distance() {
        double distance = geoCalculator.distance(53.32055555555556, -1.7297222222222221, 53.31861111111111, -1.6997222222222223);

        assertThat(distance).isEqualTo(2.0043678382716137);
    }

    @Test
    public void calculateSquareBoundary() {
        final BigDecimal lat1 = BigDecimal.valueOf(53.32055555555556);
        final BigDecimal lon1 = BigDecimal.valueOf(-1.7297222222222221);

        final SquareBoundary square = geoCalculator.calculateSquareBoundary(lat1, lon1, BigDecimal.valueOf(10.0));

        double dLatLeft = Math.ceil(geoCalculator.distance(lat1.doubleValue(), lon1.doubleValue(), square.eastLatitude()
            .doubleValue(), lon1.doubleValue()));
        double dLatRight = Math.ceil(geoCalculator.distance(lat1.doubleValue(), lon1.doubleValue(), square.westLatitude()
            .doubleValue(), lon1.doubleValue()));
        double dLatTop = Math.ceil(geoCalculator.distance(lat1.doubleValue(), lon1.doubleValue(), lat1.doubleValue(), square.northLongitude()
            .doubleValue()));
        double dLatBottom = Math.ceil(geoCalculator.distance(lat1.doubleValue(), lon1.doubleValue(), lat1.doubleValue(), square.southLongitude()
            .doubleValue()));
        assertThat(dLatLeft).isEqualTo(10d);
        assertThat(dLatRight).isEqualTo(10d);
        assertThat(dLatTop).isEqualTo(10d);
        assertThat(dLatBottom).isEqualTo(10d);
    }
}