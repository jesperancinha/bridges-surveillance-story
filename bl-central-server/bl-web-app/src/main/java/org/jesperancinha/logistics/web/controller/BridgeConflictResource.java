package org.jesperancinha.logistics.web.controller;

import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.repositories.BridgeRepository;
import org.jesperancinha.logistics.web.services.BridgeOpeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequestMapping("/conflicts")
@RestController
public class BridgeConflictResource {

    private final BridgeOpeningService bridgeOpeningService;
    private final BridgeRepository bridgeRepository;

    public BridgeConflictResource(BridgeOpeningService bridgeOpeningService, BridgeRepository bridgeRepository) {
        this.bridgeOpeningService = bridgeOpeningService;
        this.bridgeRepository = bridgeRepository;
        this.bridgeRepository.save(new Bridge());
    }

    @GetMapping
    public ResponseEntity getAllConflicts() {
        return ResponseEntity.ok(bridgeOpeningService.getAllConflicts(new HashMap<>()));
    }
}
