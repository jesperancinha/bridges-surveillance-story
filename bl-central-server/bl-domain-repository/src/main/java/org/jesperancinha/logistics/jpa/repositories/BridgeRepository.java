package org.jesperancinha.logistics.jpa.repositories;

import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.model.BridgeOpeningTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BridgeRepository extends CrudRepository<Bridge, Long> {
    List<Bridge> findBridgeBySquareBoundary(
        @Param("latWest")
            BigDecimal latWest,
        @Param("latEast")
            BigDecimal latEast,
        @Param("lonNorth")
            BigDecimal lonNorth,
        @Param("lonSouth")
            BigDecimal lonSouth);
}
