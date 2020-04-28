package org.jesperancinha.logistics.sensor.collector.converter;

import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.model.BridgeLog;
import org.jesperancinha.logistics.sensor.collector.data.BridgeLogDto;

public class BridgeConverter {
    public static BridgeLog toModel(BridgeLogDto bridgeLogDto, Bridge bridge) {
        return BridgeLog.builder()
            .checkInOut(bridgeLogDto.type())
            .timestamp(bridgeLogDto.timestamp())
            .bridge(bridge)
            .build();
    }
}
