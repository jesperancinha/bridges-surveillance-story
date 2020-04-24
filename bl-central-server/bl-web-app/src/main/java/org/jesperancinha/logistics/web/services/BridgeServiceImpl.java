package org.jesperancinha.logistics.web.services;

import org.jesperancinha.logistics.jpa.model.Bridge;
import org.jesperancinha.logistics.jpa.model.BridgeOpeningTime;
import org.jesperancinha.logistics.jpa.repositories.BridgeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.jesperancinha.logistics.web.utils.GeoCalculator.calculateSquareBoundary;

@Service
public class BridgeServiceImpl implements BridgeService {

    private final BridgeRepository bridgeRepository;

    public BridgeServiceImpl(BridgeRepository bridgeRepository) {

        this.bridgeRepository = bridgeRepository;
    }

    @Override
    public boolean isInArea(final BigDecimal lat, final BigDecimal lon) {
        SquareBoundary squareBoundary = calculateSquareBoundary(lat, lon, BigDecimal.ONE);
        List<Bridge> bridgeByLatAndLonUnderRadius = bridgeRepository.findBridgeBySquareBoundary(squareBoundary.westLatitude(), squareBoundary.eastLatitude(), squareBoundary.northLongitude(), squareBoundary.southLongitude());

        return bridgeByLatAndLonUnderRadius != null && bridgeByLatAndLonUnderRadius.size() == 1;
    }

}
