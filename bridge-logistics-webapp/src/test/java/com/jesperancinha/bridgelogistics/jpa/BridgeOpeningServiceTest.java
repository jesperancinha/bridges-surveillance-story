package com.jesperancinha.bridgelogistics.jpa;


import com.jesperancinha.bridgelogistics.controllers.data.BridgeDto;
import com.jesperancinha.bridgelogistics.controllers.data.BridgeOpeningConflictDto;
import com.jesperancinha.bridgelogistics.controllers.data.BridgeOpeningTimeDto;
import com.jesperancinha.bridgelogistics.services.BridgeOpeningService;
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
    private final BridgeOpeningTimeDto bridgeOpeningTimeDto1 = new BridgeOpeningTimeDto();
    private final BridgeOpeningTimeDto bridgeOpeningTimeDto2 = new BridgeOpeningTimeDto();
    private final BridgeOpeningTimeDto bridgeOpeningTimeDto3 = new BridgeOpeningTimeDto();
    private final BridgeOpeningTimeDto bridgeOpeningTimeDto4 = new BridgeOpeningTimeDto();
    private final BridgeOpeningTimeDto bridgeOpeningTimeDto5 = new BridgeOpeningTimeDto();
    private BridgeOpeningService bridgeOpeningService;

    @Before
    public void setUp() {
        List<BridgeOpeningTimeDto> testCases = new ArrayList<>();
        bridgeOpeningTimeDto1.setBridgeName(BRIDGE_ONE);
        bridgeOpeningTimeDto1.setOpeningTime(LocalDateTime.of(2016, 11, 1, 10, 10, 0));
        bridgeOpeningTimeDto1.setClosingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        testCases.add(bridgeOpeningTimeDto1);
        bridgeOpeningTimeDto2.setOpeningTime(LocalDateTime.of(2016, 11, 1, 11, 10, 0));
        bridgeOpeningTimeDto2.setClosingTime(LocalDateTime.of(2016, 11, 1, 13, 10, 0));
        bridgeOpeningTimeDto2.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningTimeDto2);
        bridgeOpeningTimeDto3.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningTimeDto3.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningTimeDto3.setBridgeName(BRIDGE_ONE);
        testCases.add(bridgeOpeningTimeDto3);
        bridgeOpeningTimeDto4.setOpeningTime(LocalDateTime.of(2016, 11, 1, 9, 10, 0));
        bridgeOpeningTimeDto4.setClosingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        bridgeOpeningTimeDto4.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningTimeDto4);
        bridgeOpeningTimeDto5.setOpeningTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        bridgeOpeningTimeDto5.setClosingTime(LocalDateTime.of(2016, 11, 1, 20, 10, 0));
        bridgeOpeningTimeDto5.setBridgeName(BRIDGE_TWO);
        testCases.add(bridgeOpeningTimeDto5);
        this.bridgeOpeningService = new BridgeOpeningService(testCases);
    }

    @Test
    public void testConflictsBridgeOneOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final Set<BridgeOpeningTimeDto> bridgeOpeningTimeDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningTimeDtos).isNotNull();
        assertThat(bridgeOpeningTimeDtos).hasSize(3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto1 = bridgeOne.get(bridgeOpeningTimeDto1);
        assertThat(bridgeOpeningConflictDto1).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict1 = bridgeOpeningConflictDto1.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict1).isNotNull();
        assertThat(relatedElementsConflict1).hasSize(2);
        assertThat(relatedElementsConflict1).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto2 = bridgeOne.get(bridgeOpeningTimeDto2);
        assertThat(bridgeOpeningConflictDto2).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict2 = bridgeOpeningConflictDto2.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict2).isNotNull();
        assertThat(relatedElementsConflict2).hasSize(3);
        assertThat(relatedElementsConflict2).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto3 = bridgeOne.get(bridgeOpeningTimeDto3);
        assertThat(bridgeOpeningConflictDto3).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict3 = bridgeOpeningConflictDto3.getRelatedOpeningTimes();
        assertThat(relatedElementsConflict3).isNotNull();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
    }

    @Test
    public void testConflictsBridgeTwoOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.detectAllConflicts();

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> bridgeTwo = bridgeOpeningConflicts.get(BRIDGE_TWO);
        assertThat(bridgeTwo).isNotNull();
        final Set<BridgeOpeningTimeDto> bridgeOpeningTimeDtos2 = bridgeTwo.keySet();
        assertThat(bridgeOpeningTimeDtos2).isNotNull();
        assertThat(bridgeOpeningTimeDtos2).hasSize(2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto4 = bridgeTwo.get(bridgeOpeningTimeDto4);
        assertThat(bridgeOpeningConflictDto4).isNotNull();
        final List<BridgeOpeningTimeDto> relatedOpeningTimes4ForBridge2 = bridgeOpeningConflictDto4.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes4ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes4ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningTimeDto4, bridgeOpeningTimeDto5);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto5 = bridgeTwo.get(bridgeOpeningTimeDto5);
        assertThat(bridgeOpeningConflictDto5).isNotNull();
        final List<BridgeOpeningTimeDto> relatedOpeningTimes5ForBridge2 = bridgeOpeningConflictDto5.getRelatedOpeningTimes();
        assertThat(relatedOpeningTimes5ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes5ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningTimeDto5, bridgeOpeningTimeDto4);
    }
}
