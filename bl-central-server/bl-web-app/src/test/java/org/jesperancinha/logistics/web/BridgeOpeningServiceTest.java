package org.jesperancinha.logistics.web;

import org.jesperancinha.logistics.jpa.repositories.BridgeRepository;
import org.jesperancinha.logistics.web.data.BridgeDto;
import org.jesperancinha.logistics.web.data.BridgeOpeningConflictDto;
import org.jesperancinha.logistics.web.data.BridgeOpeningTimeDto;
import org.jesperancinha.logistics.web.services.BridgeOpeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

public class BridgeOpeningServiceTest {

    private static final BridgeDto BRIDGE_ONE =  BridgeDto.builder().name("25 de Abril").build();
    private static final BridgeDto BRIDGE_TWO =  BridgeDto.builder().name("Vasco da Gama").build();
    private BridgeOpeningTimeDto bridgeOpeningTimeDto1;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto2;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto3;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto4;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto5;
    private BridgeOpeningService bridgeOpeningService;
    private final List<BridgeOpeningTimeDto> testCases = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        BridgeRepository bridgeRepository = null;
        bridgeOpeningService = new BridgeOpeningService();
        bridgeOpeningTimeDto1 = BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_ONE)
            .openingTime(of(2016, 11, 1, 10, 10, 0))
            .closingTime(of(2016, 11, 1, 12, 10, 0))
            .build();
        testCases.add(bridgeOpeningTimeDto1);
        bridgeOpeningTimeDto2 = BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_ONE)
            .openingTime(of(2016, 11, 1, 11, 10, 0))
            .closingTime(of(2016, 11, 1, 13, 10, 0))
            .build();
        bridgeOpeningTimeDto3 = BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_ONE)
            .openingTime(of(2016, 11, 1, 12, 10, 0))
            .closingTime(of(2016, 11, 1, 14, 10, 0))
            .build();
        bridgeOpeningTimeDto4 = BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_TWO)
            .openingTime(of(2016, 11, 1, 9, 10, 0))
            .closingTime(of(2016, 11, 1, 14, 10, 0))
            .build();
        bridgeOpeningTimeDto5 = BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_TWO)
            .openingTime(of(2016, 11, 1, 12, 10, 0))
            .closingTime(of(2016, 11, 1, 20, 10, 0))
            .build();
        testCases.add(bridgeOpeningTimeDto2);
        testCases.add(bridgeOpeningTimeDto3);
        testCases.add(bridgeOpeningTimeDto4);
        testCases.add(bridgeOpeningTimeDto5);
    }

    @Test
    public void testConflictsBridgeOneOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.getAllConflicts(testCases);

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final Set<BridgeOpeningTimeDto> bridgeOpeningTimeDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningTimeDtos).isNotNull();
        assertThat(bridgeOpeningTimeDtos).hasSize(3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto1 = bridgeOne.get(bridgeOpeningTimeDto1);
        assertThat(bridgeOpeningConflictDto1).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict1 = bridgeOpeningConflictDto1.relatedOpeningTimes();
        assertThat(relatedElementsConflict1).isNotNull();
        assertThat(relatedElementsConflict1).hasSize(2);
        assertThat(relatedElementsConflict1).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto2 = bridgeOne.get(bridgeOpeningTimeDto2);
        assertThat(bridgeOpeningConflictDto2).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict2 = bridgeOpeningConflictDto2.relatedOpeningTimes();
        assertThat(relatedElementsConflict2).isNotNull();
        assertThat(relatedElementsConflict2).hasSize(3);
        assertThat(relatedElementsConflict2).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto3 = bridgeOne.get(bridgeOpeningTimeDto3);
        assertThat(bridgeOpeningConflictDto3).isNotNull();
        final List<BridgeOpeningTimeDto> relatedElementsConflict3 = bridgeOpeningConflictDto3.relatedOpeningTimes();
        assertThat(relatedElementsConflict3).isNotNull();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
    }

    @Test
    public void testConflictsBridgeTwoOk() {
        final Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> bridgeOpeningConflicts = bridgeOpeningService.getAllConflicts(testCases);

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> bridgeTwo = bridgeOpeningConflicts.get(BRIDGE_TWO);
        assertThat(bridgeTwo).isNotNull();
        final Set<BridgeOpeningTimeDto> bridgeOpeningTimeDtos2 = bridgeTwo.keySet();
        assertThat(bridgeOpeningTimeDtos2).isNotNull();
        assertThat(bridgeOpeningTimeDtos2).hasSize(2);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto4 = bridgeTwo.get(bridgeOpeningTimeDto4);
        assertThat(bridgeOpeningConflictDto4).isNotNull();
        final List<BridgeOpeningTimeDto> relatedOpeningTimes4ForBridge2 = bridgeOpeningConflictDto4.relatedOpeningTimes();
        assertThat(relatedOpeningTimes4ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes4ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningTimeDto4, bridgeOpeningTimeDto5);
        final BridgeOpeningConflictDto bridgeOpeningConflictDto5 = bridgeTwo.get(bridgeOpeningTimeDto5);
        assertThat(bridgeOpeningConflictDto5).isNotNull();
        final List<BridgeOpeningTimeDto> relatedOpeningTimes5ForBridge2 = bridgeOpeningConflictDto5.relatedOpeningTimes();
        assertThat(relatedOpeningTimes5ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes5ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningTimeDto5, bridgeOpeningTimeDto4);
    }
}
