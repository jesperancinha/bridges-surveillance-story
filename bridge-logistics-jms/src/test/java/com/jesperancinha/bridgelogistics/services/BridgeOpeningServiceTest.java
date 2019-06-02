package com.jesperancinha.bridgelogistics.services;


import static org.assertj.core.api.Assertions.assertThat;

import com.jesperancinha.bridgelogistics.data.BridgeOpeningConflictDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BridgeOpeningServiceTest {

    private static final String BRIDGE_ONE = "bridgeOne";
    private static final String BRIDGE_TWO = "bridgeTwo";
    private final BridgeOpeningDto bridgeOpeningDto1 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto2 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto3 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto4 = new BridgeOpeningDto();
    private final BridgeOpeningDto bridgeOpeningDto5 = new BridgeOpeningDto();
    private BridgeOpeningService bridgeOpeningService;

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
        bridgeOpeningDto4.setOpeningTime(LocalDateTime.of(2016, 11, 1, 9, 10, 0));
        bridgeOpeningDto4.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningDto4.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningDto4);
        bridgeOpeningDto5.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningDto5.setClosingTime(LocalDateTime.of(2016, 11, 1, 20, 10, 0));
        bridgeOpeningDto5.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningDto5);
        this.bridgeOpeningService = new BridgeOpeningService(testCases);
    }

    @Test
    public void testConflictsBridgeOneOk() {
        final Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningDto, BridgeOpeningConflictDto> bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final Set<BridgeOpeningDto> bridgeOpeningDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningDtos).isNotNull();
        assertThat(bridgeOpeningDtos).hasSize(3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto1 = bridgeOne.get(bridgeOpeningDto1);
        assertThat(bridgeOpeningConflictDto1).isNotNull();
        final List<BridgeOpeningDto> relatedElementsConflict1 = bridgeOpeningConflictDto1.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict1).isNotNull();
        assertThat(relatedElementsConflict1).hasSize(2);
        assertThat(relatedElementsConflict1).contains(bridgeOpeningDto1, bridgeOpeningDto2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto2 = bridgeOne.get(bridgeOpeningDto2);
        assertThat(bridgeOpeningConflictDto2).isNotNull();
        final List<BridgeOpeningDto> relatedElementsConflict2 = bridgeOpeningConflictDto2.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict2).isNotNull();
        assertThat(relatedElementsConflict2).hasSize(3);
        assertThat(relatedElementsConflict2).contains(bridgeOpeningDto1, bridgeOpeningDto2, bridgeOpeningDto3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto3 = bridgeOne.get(bridgeOpeningDto3);
        assertThat(bridgeOpeningConflictDto3).isNotNull();
        final List<BridgeOpeningDto> relatedElementsConflict3 = bridgeOpeningConflictDto3.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict3).isNotNull();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(bridgeOpeningDto2, bridgeOpeningDto3);
    }

    @Test
    public void testConflictsBridgeTwoOk() {
        final Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningDto, BridgeOpeningConflictDto> bridgeTwo = bridgeOpeningConflicts.get(BRIDGE_TWO);
        assertThat(bridgeTwo).isNotNull();
        final Set<BridgeOpeningDto> bridgeOpeningDtos2 = bridgeTwo.keySet();
        assertThat(bridgeOpeningDtos2).isNotNull();
        assertThat(bridgeOpeningDtos2).hasSize(2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto4 = bridgeTwo.get(bridgeOpeningDto4);
        assertThat(bridgeOpeningConflictDto4).isNotNull();
        final List<BridgeOpeningDto> relatedOpeningTimes4ForBridge2 = bridgeOpeningConflictDto4.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes4ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes4ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningDto4, bridgeOpeningDto5);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto5 = bridgeTwo.get(bridgeOpeningDto5);
        assertThat(bridgeOpeningConflictDto5).isNotNull();
        final List<BridgeOpeningDto> relatedOpeningTimes5ForBridge2 = bridgeOpeningConflictDto5.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes5ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes5ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningDto5, bridgeOpeningDto4);
    }
}
