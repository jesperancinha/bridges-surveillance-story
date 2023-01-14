package org.jesperancinha.logistics.web.services;

import org.jesperancinha.logistics.jpa.dao.Bridge;
import org.jesperancinha.logistics.jpa.dao.BridgeRepository;
import org.jesperancinha.logistics.web.utils.GeoCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class BridgeServiceImpl implements BridgeService {

    private final BridgeRepository bridgeRepository;
    private final GeoCalculator geoCalculator;

    public BridgeServiceImpl(BridgeRepository bridgeRepository, GeoCalculator geoCalculator) {

        this.bridgeRepository = bridgeRepository;
        this.geoCalculator = geoCalculator;
    }

    @Override
    public boolean isInArea(final BigDecimal lat, final BigDecimal lon) {
        SquareBoundary squareBoundary = geoCalculator.calculateSquareBoundary(lat, lon, BigDecimal.ONE);
        List<Bridge> bridgeByLatAndLonUnderRadius = bridgeRepository.findBridgeBySquareBoundary(squareBoundary.westLatitude(), squareBoundary.eastLatitude(), squareBoundary.northLongitude(), squareBoundary.southLongitude());

        return bridgeByLatAndLonUnderRadius != null && bridgeByLatAndLonUnderRadius.size() == 1;
    }

}
