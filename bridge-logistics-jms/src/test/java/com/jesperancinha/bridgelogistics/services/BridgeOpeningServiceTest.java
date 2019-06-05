package com.jesperancinha.bridgelogistics.services;


import com.jesperancinha.bridgelogistics.data.BridgeDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningConflictDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningTimesDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BridgeOpeningServiceTest {

    private static final BridgeDto BRIDGE_ONE = new BridgeDto();
    private static final BridgeDto BRIDGE_TWO = new BridgeDto();
    private final BridgeOpeningTimesDto bridgeOpeningTimesDto1 = new BridgeOpeningTimesDto();
    private final BridgeOpeningTimesDto bridgeOpeningTimesDto2 = new BridgeOpeningTimesDto();
    private final BridgeOpeningTimesDto bridgeOpeningTimesDto3 = new BridgeOpeningTimesDto();
    private final BridgeOpeningTimesDto bridgeOpeningTimesDto4 = new BridgeOpeningTimesDto();
    private final BridgeOpeningTimesDto bridgeOpeningTimesDto5 = new BridgeOpeningTimesDto();
    private BridgeOpeningService bridgeOpeningService;

    @Before
    public void setUp() {
        List<BridgeOpeningTimesDto> testCases = new ArrayList<>();
        bridgeOpeningTimesDto1.setBridgeName(BRIDGE_ONE);
        bridgeOpeningTimesDto1.setOpeningTime(LocalDateTime.of(2016, 11, 1, 10, 10, 0));
        bridgeOpeningTimesDto1.setClosingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        testCases.add(bridgeOpeningTimesDto1);
        bridgeOpeningTimesDto2.setOpeningTime(LocalDateTime.of(2016, 11, 1, 11, 10, 0));
        bridgeOpeningTimesDto2.setClosingTime(LocalDateTime.of(2016, 11, 1, 13, 10, 0));
        bridgeOpeningTimesDto2.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningTimesDto2);
        bridgeOpeningTimesDto3.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningTimesDto3.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningTimesDto3.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningTimesDto3);
        bridgeOpeningTimesDto4.setOpeningTime(LocalDateTime.of(2016, 11, 1, 9, 10, 0));
        bridgeOpeningTimesDto4.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningTimesDto4.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningTimesDto4);
        bridgeOpeningTimesDto5.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningTimesDto5.setClosingTime(LocalDateTime.of(2016, 11, 1, 20, 10, 0));
        bridgeOpeningTimesDto5.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningTimesDto5);
        this.bridgeOpeningService = new BridgeOpeningService(testCases);
    }

    @Test
    public void testConflictsBridgeOneOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final Set<BridgeOpeningTimesDto> bridgeOpeningTimesDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningTimesDtos).isNotNull();
        assertThat(bridgeOpeningTimesDtos).hasSize(3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto1 = bridgeOne.get(bridgeOpeningTimesDto1);
        assertThat(bridgeOpeningConflictDto1).isNotNull();
        final List<BridgeOpeningTimesDto> relatedElementsConflict1 = bridgeOpeningConflictDto1.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict1).isNotNull();
        assertThat(relatedElementsConflict1).hasSize(2);
        assertThat(relatedElementsConflict1).contains(bridgeOpeningTimesDto1, bridgeOpeningTimesDto2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto2 = bridgeOne.get(bridgeOpeningTimesDto2);
        assertThat(bridgeOpeningConflictDto2).isNotNull();
        final List<BridgeOpeningTimesDto> relatedElementsConflict2 = bridgeOpeningConflictDto2.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict2).isNotNull();
        assertThat(relatedElementsConflict2).hasSize(3);
        assertThat(relatedElementsConflict2).contains(bridgeOpeningTimesDto1, bridgeOpeningTimesDto2, bridgeOpeningTimesDto3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto3 = bridgeOne.get(bridgeOpeningTimesDto3);
        assertThat(bridgeOpeningConflictDto3).isNotNull();
        final List<BridgeOpeningTimesDto> relatedElementsConflict3 = bridgeOpeningConflictDto3.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict3).isNotNull();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(bridgeOpeningTimesDto2, bridgeOpeningTimesDto3);
    }

    @Test
    public void testConflictsBridgeTwoOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> bridgeTwo = bridgeOpeningConflicts.get(BRIDGE_TWO);
        assertThat(bridgeTwo).isNotNull();
        final Set<BridgeOpeningTimesDto> bridgeOpeningTimesDtos2 = bridgeTwo.keySet();
        assertThat(bridgeOpeningTimesDtos2).isNotNull();
        assertThat(bridgeOpeningTimesDtos2).hasSize(2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto4 = bridgeTwo.get(bridgeOpeningTimesDto4);
        assertThat(bridgeOpeningConflictDto4).isNotNull();
        final List<BridgeOpeningTimesDto> relatedOpeningTimes4ForBridge2 = bridgeOpeningConflictDto4.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes4ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes4ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningTimesDto4, bridgeOpeningTimesDto5);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto5 = bridgeTwo.get(bridgeOpeningTimesDto5);
        assertThat(bridgeOpeningConflictDto5).isNotNull();
        final List<BridgeOpeningTimesDto> relatedOpeningTimes5ForBridge2 = bridgeOpeningConflictDto5.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes5ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes5ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningTimesDto5, bridgeOpeningTimesDto4);
    }
}
