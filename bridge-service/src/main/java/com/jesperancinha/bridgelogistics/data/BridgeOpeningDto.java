package com.jesperancinha.bridgelogistics.data;

import java.time.LocalDateTime;

/**
 * This object serves to determine a time slot to open and close a bridge
 */
public class BridgeOpeningDto {

    private String bridgeName;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;

    public String getBridgeName() {
        return bridgeName;
    }

    public void setBridgeName(String bridgeName) {
        this.bridgeName = bridgeName;
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
