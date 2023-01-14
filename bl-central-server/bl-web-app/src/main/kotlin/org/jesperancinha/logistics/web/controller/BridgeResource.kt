package org.jesperancinha.logistics.web.controller

import org.jesperancinha.logistics.web.services.BridgeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RequestMapping("/schedules")
@RestController
class BridgeResource(private val bridgeService: BridgeService) {
    /**
     * We consider a car to be in the vicinity of a bridge if its coordinates fall into the radius determined per bridge.
     * All measurements in Km
     * @return
     */
    @GetMapping("/area/{lat}/{lon}")
    fun isInArea(
        @PathVariable lat: BigDecimal,
        @PathVariable lon: BigDecimal
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(bridgeService.isInArea(lat, lon))
    }
}