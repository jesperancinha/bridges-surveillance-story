package com.jesperancinha.bridgelogistics.data;

import java.time.LocalDateTime;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
public class BridgeOpeningDto {

    private BridgeDto bridge;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    public BridgeDto getBridgeName() {
        return bridge;
    }

    public void setBridgeName(BridgeDto bridgeName) {
        this.bridge = bridgeName;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }
}
