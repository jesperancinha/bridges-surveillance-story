package org.jesperancinha.logistics.mcs.model;

public record Location(String address,
    String postCode,
    String city,
    String country,
    Long longitude,
    Long latitude) {
}
