package org.jesperancinha.logistics.web

import org.assertj.core.api.Assertions
import org.jesperancinha.logistics.jpa.dao.OpeningTimeRepository
import org.jesperancinha.logistics.web.data.BridgeDto
import org.jesperancinha.logistics.web.data.BridgeOpeningConflictDto
import org.jesperancinha.logistics.web.data.BridgeOpeningTimeDto
import org.jesperancinha.logistics.web.services.BridgeOpeningService
import org.jesperancinha.logistics.web.utils.GeoCalculator
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [BridgeOpeningService::class, GeoCalculator::class])
class BridgeOpeningServiceTest @Autowired constructor(
    @Autowired
    private val bridgeOpeningService: BridgeOpeningService
) {
    private val bridgeOpeningTimeDto1 by lazy { BridgeOpeningTimeDto.builder()
        .bridge(BRIDGE_ONE)
        .openingTime(LocalDateTime.of(2016, 11, 1, 10, 10, 0))
        .closingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0))
        .build() }
    private val bridgeOpeningTimeDto2 by lazy {
        BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_ONE)
            .openingTime(LocalDateTime.of(2016, 11, 1, 11, 10, 0))
            .closingTime(LocalDateTime.of(2016, 11, 1, 13, 10, 0))
            .build()
    }
    private val bridgeOpeningTimeDto3 by lazy {
        BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_ONE)
            .openingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0))
            .closingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0))
            .build()
    }
    private val bridgeOpeningTimeDto4 by lazy {
        BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_TWO)
            .openingTime(LocalDateTime.of(2016, 11, 1, 9, 10, 0))
            .closingTime(LocalDateTime.of(2016, 11, 1, 14, 10, 0))
            .build()
    }
    private val bridgeOpeningTimeDto5: BridgeOpeningTimeDto by lazy {
        BridgeOpeningTimeDto.builder()
            .bridge(BRIDGE_TWO)
            .openingTime(LocalDateTime.of(2016, 11, 1, 12, 10, 0))
            .closingTime(LocalDateTime.of(2016, 11, 1, 20, 10, 0))
            .build()
    }


    private val testCases: MutableList<BridgeOpeningTimeDto> = ArrayList()

    @MockBean
    private val openingTimeRepository: OpeningTimeRepository? = null
    @BeforeEach
    fun setUp() {
        testCases.add(bridgeOpeningTimeDto1)
        testCases.add(bridgeOpeningTimeDto2)
        testCases.add(bridgeOpeningTimeDto3)
        testCases.add(bridgeOpeningTimeDto4)
        testCases.add(bridgeOpeningTimeDto5)
    }

    @Test
    fun testConflictsBridgeOneOk() {
        val bridgeOpeningConflicts =
            bridgeOpeningService.getAllConflicts(testCases)
        Assertions.assertThat(bridgeOpeningConflicts).hasSize(2)
        val bridgeOne = bridgeOpeningConflicts[BRIDGE_ONE]!!
        Assertions.assertThat(bridgeOne).isNotNull
        val bridgeOpeningTimeDtos = bridgeOne.keys
        Assertions.assertThat(bridgeOpeningTimeDtos).isNotNull
        Assertions.assertThat(bridgeOpeningTimeDtos).hasSize(3)
        val bridgeOpeningConflictDto1 = bridgeOne[bridgeOpeningTimeDto1]
        Assertions.assertThat(bridgeOpeningConflictDto1).isNotNull
        val relatedElementsConflict1: List<BridgeOpeningTimeDto> = bridgeOpeningConflictDto1!!.relatedOpeningTimes
        Assertions.assertThat(relatedElementsConflict1).isNotNull
        Assertions.assertThat(relatedElementsConflict1).hasSize(2)
        Assertions.assertThat(relatedElementsConflict1).contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2)
        val bridgeOpeningConflictDto2 = bridgeOne[bridgeOpeningTimeDto2]
        Assertions.assertThat(bridgeOpeningConflictDto2).isNotNull
        val relatedElementsConflict2: List<BridgeOpeningTimeDto> = bridgeOpeningConflictDto2!!.relatedOpeningTimes
        Assertions.assertThat(relatedElementsConflict2).isNotNull
        Assertions.assertThat(relatedElementsConflict2).hasSize(3)
        Assertions.assertThat(relatedElementsConflict2)
            .contains(bridgeOpeningTimeDto1, bridgeOpeningTimeDto2, bridgeOpeningTimeDto3)
        val bridgeOpeningConflictDto3 = bridgeOne[bridgeOpeningTimeDto3]
        Assertions.assertThat(bridgeOpeningConflictDto3).isNotNull
        val relatedElementsConflict3: List<BridgeOpeningTimeDto> = bridgeOpeningConflictDto3!!.relatedOpeningTimes
        Assertions.assertThat(relatedElementsConflict3).isNotNull
        Assertions.assertThat(relatedElementsConflict3).hasSize(2)
        Assertions.assertThat(relatedElementsConflict3).contains(bridgeOpeningTimeDto2, bridgeOpeningTimeDto3)
    }

    @Test
    fun testConflictsBridgeTwoOk() {
        val bridgeOpeningConflicts = bridgeOpeningService.getAllConflicts(testCases)
        Assertions.assertThat(bridgeOpeningConflicts).hasSize(2)
        val bridgeTwo = bridgeOpeningConflicts[BRIDGE_TWO]!!
        Assertions.assertThat(bridgeTwo).isNotNull
        val bridgeOpeningTimeDtos2 = bridgeTwo.keys
        Assertions.assertThat(bridgeOpeningTimeDtos2).isNotNull
        Assertions.assertThat(bridgeOpeningTimeDtos2).hasSize(2)
        val bridgeOpeningConflictDto4 = bridgeTwo[bridgeOpeningTimeDto4]
        Assertions.assertThat(bridgeOpeningConflictDto4).isNotNull
        val relatedOpeningTimes4ForBridge2: List<BridgeOpeningTimeDto> = bridgeOpeningConflictDto4!!.relatedOpeningTimes
        Assertions.assertThat(relatedOpeningTimes4ForBridge2).isNotNull
        Assertions.assertThat(relatedOpeningTimes4ForBridge2).hasSize(2)
        Assertions.assertThat(relatedOpeningTimes4ForBridge2).contains(bridgeOpeningTimeDto4, bridgeOpeningTimeDto5)
        val bridgeOpeningConflictDto5 = bridgeTwo[bridgeOpeningTimeDto5]
        Assertions.assertThat(bridgeOpeningConflictDto5).isNotNull
        val relatedOpeningTimes5ForBridge2: List<BridgeOpeningTimeDto> = bridgeOpeningConflictDto5!!.relatedOpeningTimes
        Assertions.assertThat(relatedOpeningTimes5ForBridge2).isNotNull
        Assertions.assertThat(relatedOpeningTimes5ForBridge2).hasSize(2)
        Assertions.assertThat(relatedOpeningTimes5ForBridge2).contains(bridgeOpeningTimeDto5, bridgeOpeningTimeDto4)
    }

    @AfterEach
    fun tearDown() {
        Mockito.verifyNoInteractions(openingTimeRepository)
    }

    companion object {
        private val BRIDGE_ONE = BridgeDto.builder()
            .name("25 de Abril")
            .build()
        private val BRIDGE_TWO = BridgeDto.builder()
            .name("Vasco da Gama")
            .build()
    }
}