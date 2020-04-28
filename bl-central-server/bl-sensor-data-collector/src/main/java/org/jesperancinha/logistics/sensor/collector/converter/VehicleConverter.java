package org.jesperancinha.logistics.sensor.collector.converter;

import org.jesperancinha.logistics.jpa.model.Vehicle;
import org.jesperancinha.logistics.jpa.model.VehicleLog;
import org.jesperancinha.logistics.sensor.collector.data.VehicleLogDto;

public class VehicleConverter {
    public static VehicleLog toModel(VehicleLogDto vehicleLogDto, Vehicle vehicle) {
        return VehicleLog.builder()
            .checkInOut(vehicleLogDto.type())
            .timestamp(vehicleLogDto.timestamp())
            .vehicle(vehicle)
            .lat(vehicleLogDto.lat())
            .lon(vehicleLogDto.lon())
            .build();
    }
}
