package org.jesperancinha.logistics.controllers.data;

import java.util.List;

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
public class BridgeOpeningConflictDto {

    private String message;

    private List<BridgeOpeningTimeDto> relatedOpeningTimes;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BridgeOpeningTimeDto> getRelatedOpeningTimes() {
        return relatedOpeningTimes;
    }

    public void setRelatedOpeningTimes(List<BridgeOpeningTimeDto> relatedOpeningTimes) {
        this.relatedOpeningTimes = relatedOpeningTimes;
    }
}
