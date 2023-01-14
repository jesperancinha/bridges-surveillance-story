package org.jesperancinha.logistics.sensor.collector.converter

import org.jesperancinha.logistics.jpa.dao.Train
import org.jesperancinha.logistics.jpa.dao.TrainLog
import org.jesperancinha.logistics.sensor.collector.data.TrainLogDto

object TrainConverter {
    fun toModel(trainLogDto: TrainLogDto, train: Train?): TrainLog {
        return TrainLog(
            checkInOut = trainLogDto.type,
            timestamp = trainLogDto.timestamp,
            train = train,
            lat = trainLogDto.lat,
            lon = trainLogDto.lon,
            weight = trainLogDto.weight,
            carriageId = trainLogDto.carriageId
        )
    }
}