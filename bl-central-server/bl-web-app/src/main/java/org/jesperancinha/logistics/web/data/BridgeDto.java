package org.jesperancinha.logistics.web.data;

import lombok.Builder;

public record BridgeDto(
    String name,
    String address,
    String city,
    String postCode,
    String country) {

    @Builder
    public BridgeDto{
    }

    public BridgeDto() {
        this(null, null, null, null, null);
    }
}
