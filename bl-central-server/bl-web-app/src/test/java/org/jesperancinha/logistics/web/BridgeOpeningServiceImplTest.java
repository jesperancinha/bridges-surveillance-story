package org.jesperancinha.logistics.web;

import org.jesperancinha.logistics.jpa.dao.OpeningTimeRepository;
import org.jesperancinha.logistics.web.data.BridgeDto;
import org.jesperancinha.logistics.web.data.BridgeOpeningTimeDto;
import org.jesperancinha.logistics.web.services.BridgeOpeningServiceImpl;
import org.jesperancinha.logistics.web.utils.GeoCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BridgeOpeningServiceImpl.class, GeoCalculator.class})
public class BridgeOpeningServiceImplTest {

    private static final BridgeDto BRIDGE_ONE = BridgeDto.builder()
            .name("25 de Abril")
            .build();
    private static final BridgeDto BRIDGE_TWO = BridgeDto.builder()
            .name("Vasco da Gama")
            .build();
    private BridgeOpeningTimeDto bridgeOpeningTimeDto1;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto2;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto3;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto4;
    private BridgeOpeningTimeDto bridgeOpeningTimeDto5;

    @Autowired
    private BridgeOpeningServiceImpl bridgeOpeningServiceImpl;

    private final List<BridgeOpeningTimeDto> testCases = new ArrayList<>();

    @MockBean
    private OpeningTimeRepository openingTimeRepository;

    @BeforeEach
    public void setUp() {
        bridgeOpeningTimeDto1 = BridgeOpeningTimeDto.builder()
                .bridge(BRIDGE_ONE)
                .openingTime(of(2016, 11, 1, 10, 10, 0))
                .closingTime(of(2016, 11, 1, 12, 10, 0))
                .build();
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
        testCases.add(bridgeOpeningTimeDto1);
        testCases.add(bridgeOpeningTimeDto2);
        testCases.add(bridgeOpeningTimeDto3);
        testCases.add(bridgeOpeningTimeDto4);
        testCases.add(bridgeOpeningTimeDto5);
    }

    @Test
    public void testConflictsBridgeOneOk() {
        final var bridgeOpeningConflicts
                = bridgeOpeningServiceImpl.getAllConflicts(testCases);

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final var bridgeOne = bridgeOpeningConflicts.get(BRIDGE_ONE);
        assertThat(bridgeOne).isNotNull();
        final var bridgeOpeningTimeDtos = bridgeOne.keySet();
        assertThat(bridgeOpeningTimeDtos).isNotNull();
        assertThat(bridgeOpeningTimeDtos).hasSize(3);
        final var bridgeOpeningConflictDto1 = bridgeOne.get(bridgeOpeningTimeDto1);
        assertThat(bridgeOpeningConflictDto1).isNotNull();
        final var relatedElementsConflict1 = bridgeOpeningConflictDto1.relatedOpeningTimes();
        assertThat(relatedElementsConflict1).isNotNull();
        assertThat(relatedElementsConflict1).hasSize(2);
        assertThat(relatedElementsConflict1).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2);
        final var bridgeOpeningConflictDto2 = bridgeOne.get(bridgeOpeningTimeDto2);
        assertThat(bridgeOpeningConflictDto2).isNotNull();
        final var relatedElementsConflict2 = bridgeOpeningConflictDto2.relatedOpeningTimes();
        assertThat(relatedElementsConflict2).isNotNull();
        assertThat(relatedElementsConflict2).hasSize(3);
        assertThat(relatedElementsConflict2).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
        final var bridgeOpeningConflictDto3 = bridgeOne.get(bridgeOpeningTimeDto3);
        assertThat(bridgeOpeningConflictDto3).isNotNull();
        final var relatedElementsConflict3 = bridgeOpeningConflictDto3.relatedOpeningTimes();
        assertThat(relatedElementsConflict3).isNotNull();
        assertThat(relatedElementsConflict3).hasSize(2);
        assertThat(relatedElementsConflict3).contains(bridgeOpeningTimeDto2, bridgeOpeningTimeDto3);
    }

    @Test
    public void testConflictsBridgeTwoOk() {
        final var bridgeOpeningConflicts
                = bridgeOpeningServiceImpl.getAllConflicts(testCases);

        assertThat(bridgeOpeningConflicts).hasSize(2);

        final var bridgeTwo = bridgeOpeningConflicts.get(BRIDGE_TWO);
        assertThat(bridgeTwo).isNotNull();
        final var bridgeOpeningTimeDtos2 = bridgeTwo.keySet();
        assertThat(bridgeOpeningTimeDtos2).isNotNull();
        assertThat(bridgeOpeningTimeDtos2).hasSize(2);
        final var bridgeOpeningConflictDto4 = bridgeTwo.get(bridgeOpeningTimeDto4);
        assertThat(bridgeOpeningConflictDto4).isNotNull();
        final var relatedOpeningTimes4ForBridge2 = bridgeOpeningConflictDto4.relatedOpeningTimes();
        assertThat(relatedOpeningTimes4ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes4ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningTimeDto4, bridgeOpeningTimeDto5);
        final var bridgeOpeningConflictDto5 = bridgeTwo.get(bridgeOpeningTimeDto5);
        assertThat(bridgeOpeningConflictDto5).isNotNull();
        final var relatedOpeningTimes5ForBridge2 = bridgeOpeningConflictDto5.relatedOpeningTimes();
        assertThat(relatedOpeningTimes5ForBridge2).isNotNull();
        assertThat(relatedOpeningTimes5ForBridge2).hasSize(2);
        assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningTimeDto5, bridgeOpeningTimeDto4);
    }

    @AfterEach
    public void tearDown() {
        verifyNoInteractions(openingTimeRepository);
    }
}
