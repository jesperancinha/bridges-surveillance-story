package org.jesperancinha.logistics.web.controller;

import org.jesperancinha.logistics.web.services.BridgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/schedules")
@RestController
public class BridgeResource {

    private final BridgeService bridgeService;

    public BridgeResource(BridgeService bridgeService) {
        this.bridgeService = bridgeService;
    }

    /**
     * We consider a car to be in the vicinity of a bridge if its coordinates fall into the radius determined per bridge.
     * All measurements in Km
     * @return
     */
    @GetMapping("/area/{lat}/{lon}")
    public ResponseEntity<Boolean> isInArea(
        @PathVariable
           final BigDecimal lat,
        @PathVariable
           final BigDecimal lon) {

        return ResponseEntity.ok(bridgeService.isInArea(lat, lon));
    }

}
