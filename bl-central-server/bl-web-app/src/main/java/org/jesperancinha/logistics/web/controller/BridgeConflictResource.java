package org.jesperancinha.logistics.web.controller;

import org.jesperancinha.logistics.web.services.BridgeOpeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping("/conflicts")
public class BridgeConflictResource {

    private final BridgeOpeningService bridgeOpeningService;

    public BridgeConflictResource(BridgeOpeningService bridgeOpeningService) {

        this.bridgeOpeningService = bridgeOpeningService;
    }

    @GetMapping
    public ResponseEntity getAllConflicts() {
        return ResponseEntity.ok(bridgeOpeningService.getAllConflicts(new HashMap<>()));
    }
}
