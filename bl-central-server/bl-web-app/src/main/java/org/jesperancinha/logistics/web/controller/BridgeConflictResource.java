package org.jesperancinha.logistics.web.controller;

import org.jesperancinha.logistics.web.services.BridgeOpeningService;
import org.jesperancinha.logistics.web.services.BridgeOpeningServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;

@RequestMapping("/schedules")
@RestController
public class BridgeConflictResource {

    private final BridgeOpeningService bridgeOpeningService;

    public BridgeConflictResource(BridgeOpeningServiceImpl bridgeOpeningService) {
        this.bridgeOpeningService = bridgeOpeningService;
    }

    /**
     * We consider a car to be in the vincinity of a bridge if its coordinates fall into the radius determined per bridge.
     * All measurements in Km
     * @return
     */
    @GetMapping("/open/{lat}/{lon}")
    public ResponseEntity<Boolean> isBridgeOpen(
        @PathVariable
            BigDecimal lat,
        @PathVariable
            BigDecimal lon) {

        return ResponseEntity.ok(bridgeOpeningService.isBridgeOpen(lat, lon));
    }
}
