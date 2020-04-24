package org.jesperancinha.logistics.web.utils;

import org.jesperancinha.logistics.web.services.SquareBoundary;

import java.math.BigDecimal;

import static java.lang.Math.cos;

public class GeoCalculator {

    public static final int EARTH_RADIUS = 6371;

    /**
     * Using the Haversine formula
     * Distance, d = 3963.0 * arccos[(sin(lat1) * sin(lat2)) + cos(lat1) * cos(lat2) * cos(long2 – long1)]
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLon = lon2Rad - lon1Rad;
        double deltaLat = lat2Rad - lat1Rad;

        return EARTH_RADIUS * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(deltaLon / 2), 2)));
    }

    /**
     *  lat = lat0 + (180/pi)*(dy/6378137)
     *  lon = lon0 + (180/pi)*(dx/6378137)/cos(lat0)
     * @param lat
     * @param lon
     * @return
     */
    public static SquareBoundary calculateSquareBoundary(BigDecimal lat, BigDecimal lon, BigDecimal radius) {
        BigDecimal dLat = BigDecimal.valueOf((180 / Math.PI) * (radius.doubleValue() / 6378137));
        BigDecimal dLon = BigDecimal.valueOf((180 / Math.PI) * (radius.doubleValue() / 6378137) / cos(Math.toRadians(lat.doubleValue())));
        BigDecimal latWest = lat.subtract(dLat);
        BigDecimal latEast = lat.add(dLat);
        BigDecimal lonNorth = lon.add(dLon);
        BigDecimal lonSouth = lon.subtract(dLon);
        return SquareBoundary.builder()
            .westLatitude(latWest)
            .eastLatitude(latEast)
            .northLongitude(lonNorth)
            .southLongitude(lonSouth)
            .build();
    }


}
