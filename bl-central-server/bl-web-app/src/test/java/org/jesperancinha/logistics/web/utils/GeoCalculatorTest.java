package org.jesperancinha.logistics.web.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GeoCalculatorTest {

    @Test
    public void distance() {
        double distance = GeoCalculator.distance(53.32055555555556, -1.7297222222222221, 53.31861111111111, -1.6997222222222223);

        assertThat(distance).isEqualTo(2.0043678382716137);
    }
}