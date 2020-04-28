package org.jesperancinha.logistics.sensor.collector.converter;

import org.jesperancinha.logistics.jpa.model.Train;
import org.jesperancinha.logistics.jpa.model.TrainLog;
import org.jesperancinha.logistics.sensor.collector.data.TrainLogDto;

public class TrainConverter {
    public static TrainLog toModel(TrainLogDto trainLogDto, Train train) {
        return TrainLog.builder()
            .checkInOut(trainLogDto.type())
            .timestamp(trainLogDto.timestamp())
            .train(train)
            .lat(trainLogDto.lat())
            .lon(trainLogDto.lon())
            .build();
    }
}
