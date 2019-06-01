package com.jesperancinha.bridgelogistics.services;


import static org.assertj.core.api.Assertions.assertThat;

import com.jesperancinha.bridgelogistics.BridgeOpeningConflict;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BridgeOpeningServiceTest {

    private static final String BRIDGE_ONE = "bridgeOne";

    private BridgeOpeningService bridgeOpeningService;

    private final BridgeOpeningDto bridgeOpeningDto1 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto2 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto3 = new BridgeOpeningDto();
    private final List<BridgeOpeningDto> allOpenings = new ArrayList<>(Arrays.asList(bridgeOpeningDto1, bridgeOpeningDto2, bridgeOpeningDto3));

    @Before
    public void setUp() {
        List<BridgeOpeningDto> testCases = new ArrayList<>();
        bridgeOpeningDto1.setBridgeName(BRIDGE_ONE);
        bridgeOpeningDto1.setOpeningTime(LocalDateTime.of(2016, 11, 1, 10, 10, 0));
        bridgeOpeningDto1.setClosingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        testCases.add(bridgeOpeningDto1);
        bridgeOpeningDto2.setOpeningTime(LocalDateTime.of(2016, 11, 1, 11, 10, 0));
        bridgeOpeningDto2.setClosingTime(LocalDateTime.of(2016, 11, 1, 13, 10, 0));
        bridgeOpeningDto2.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningDto2);
        bridgeOpeningDto3.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningDto3.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningDto3.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningDto3);
        this.bridgeOpeningService = new BridgeOpeningService(testCases);
    }

    @Test
    public void getAllConfilcts() {
        final Map<String, Map<BridgeOpeningDto, BridgeOpeningConflict>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(1);
        final Map<BridgeOpeningDto, BridgeOpeningConflict> bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final Set<BridgeOpeningDto> bridgeOpeningDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningDtos).hasSize(3);
        final List<BridgeOpeningDto> newOpeningOrders = new ArrayList<>(bridgeOpeningDtos);
        BridgeOpeningDto firstOpeningFound = newOpeningOrders.get(0);
        BridgeOpeningDto secondOpeningFound = newOpeningOrders.get(1);
        BridgeOpeningDto thirdOpeningFound = newOpeningOrders.get(2);
        final List<BridgeOpeningDto> relatedElementsConflict1 = bridgeOne.get(firstOpeningFound).getRelatedOpeningTimes();
        assertThat(relatedElementsConflict1).hasSize(3);
        assertThat(relatedElementsConflict1).contains(firstOpeningFound, secondOpeningFound, thirdOpeningFound);
        final List<BridgeOpeningDto> relatedElementsConflict2 = bridgeOne.get(secondOpeningFound).getRelatedOpeningTimes();
        assertThat(relatedElementsConflict2).hasSize(2);
        assertThat(relatedElementsConflict2).contains(firstOpeningFound, secondOpeningFound);
        assertThat(relatedElementsConflict2).containsAnyOf(firstOpeningFound, thirdOpeningFound);
        final List<BridgeOpeningDto> relatedElementsConflict3 = bridgeOne.get(secondOpeningFound).getRelatedOpeningTimes();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(secondOpeningFound, firstOpeningFound);
    }
}
