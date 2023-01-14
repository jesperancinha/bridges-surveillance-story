package org.jesperancinha.logistics.web.services

import org.jesperancinha.logistics.jpa.dao.OpeningTimeRepository
import org.jesperancinha.logistics.web.dto.BridgeDto
import org.jesperancinha.logistics.web.dto.BridgeOpeningConflictDto
import org.jesperancinha.logistics.web.dto.BridgeOpeningTimeDto
import org.jesperancinha.logistics.web.utils.GeoCalculator
import org.paukov.combinatorics3.Generator
import org.paukov.combinatorics3.IGenerator
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

/**
 * This service manages the bridge opening times service. It detects conflict and handles main bridge functions
 */
@Service
class BridgeOpeningService(
    private val openingTimeRepository: OpeningTimeRepository,
    private val geoCalculator: GeoCalculator
) {
    fun isBridgeOpen(lat: BigDecimal, lon: BigDecimal): Boolean {
        val (westLatitude, eastLatitude, northLongitude, southLongitude) = geoCalculator.calculateSquareBoundary(
            lat,
            lon,
            BigDecimal.ONE
        )
        val bridgeByLatAndLonUnderRadius = openingTimeRepository.findBridgeBySquareBoundaryUnderRadius(
            westLatitude, eastLatitude, northLongitude, southLongitude,
            Instant.now()
                .toEpochMilli()
        )
        return bridgeByLatAndLonUnderRadius != null && bridgeByLatAndLonUnderRadius.size == 1
    }

    /**
     * Initialize your service with an opening times map per bridge name
     *
     * @param openingTimes [Map]
     * @return
     */
    fun getAllConflicts(openingTimes: Map<BridgeDto?, List<BridgeOpeningTimeDto>?>): Map<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>> {
        return detectAllConflicts(openingTimes)
    }

    /**
     * Initialize your service with an unsorted opening times list
     *
     * @param allOpeningTimes [List]
     * @return
     */
    fun getAllConflicts(allOpeningTimes: MutableList<BridgeOpeningTimeDto>): Map<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>> {
        val openingTimes = allOpeningTimes.stream()
            .collect(Collectors.groupingBy(BridgeOpeningTimeDto::bridge))
        return detectAllConflicts(openingTimes)
    }

    /**
     * Detects all conflictual time slots in current bridge times
     *
     * @param openingTimes
     * @return All conflicts map per bridge, per opening times [Map]
     */
    fun detectAllConflicts(openingTimes: Map<BridgeDto?, List<BridgeOpeningTimeDto>?>): Map<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>> {
        val allConflicts: MutableMap<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>> = HashMap()
        openingTimes.forEach { (bridgeName: BridgeDto?, bridgeOeningTimes: List<BridgeOpeningTimeDto>?) ->
            Generator.combination(bridgeOeningTimes)
                .simple(2)
                .stream()
                .map { combination: List<BridgeOpeningTimeDto>? ->
                    Generator.permutation(combination)
                        .simple()
                }
                .flatMap { obj: IGenerator<List<BridgeOpeningTimeDto>> -> obj.stream() }
                .forEach { comb: List<BridgeOpeningTimeDto> ->
                    findConflictInATwoListBridgeOpeningTimes(
                        allConflicts,
                        comb
                    )
                }
        }
        return allConflicts
    }

    private fun findConflictInATwoListBridgeOpeningTimes(
        allConflicts: MutableMap<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>>,
        comb: List<BridgeOpeningTimeDto>
    ) {
        val opening1 = comb[0]
        val opening2 = comb[1]
        if (hasConflicts(opening1, opening2)) {
            handleConflicts(allConflicts, opening1, opening2)
        }
    }

    private fun handleConflicts(
        allConflicts: MutableMap<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>>,
        opening1: BridgeOpeningTimeDto,
        opening2: BridgeOpeningTimeDto
    ) {
        val bridge = opening1.bridge
        val currentBridgeConflicts = createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(allConflicts, bridge)
        var bridgeOpeningConflictDto = createOrGetBridgeOpeningConflict(currentBridgeConflicts, opening1)
        bridgeOpeningConflictDto!!.relatedOpeningTimes
            .addAll(listOf(opening1, opening2))
        if (currentBridgeConflicts[opening1] != null) {
            bridgeOpeningConflictDto = sanitize(bridgeOpeningConflictDto)
            currentBridgeConflicts[opening1] = bridgeOpeningConflictDto
        }
        allConflicts[bridge] = currentBridgeConflicts
    }

    private fun createOrGetBridgeOpeningConflict(
        currentBridgeConflicts: MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>,
        opening1: BridgeOpeningTimeDto
    ): BridgeOpeningConflictDto? {
        var bridgeOpeningConflictDto = currentBridgeConflicts[opening1]
        if (Objects.isNull(bridgeOpeningConflictDto)) {
            bridgeOpeningConflictDto = BridgeOpeningConflictDto(
                relatedOpeningTimes = mutableListOf()
            )
            currentBridgeConflicts[opening1] = bridgeOpeningConflictDto
        }
        return bridgeOpeningConflictDto
    }

    private fun createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(
        allConflicts: Map<BridgeDto, MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?>>,
        bridge: BridgeDto
    ): MutableMap<BridgeOpeningTimeDto, BridgeOpeningConflictDto?> {
        return allConflicts[bridge] ?: HashMap()
    }

    /**
     * This method prevents conflictual opening times to be
     *
     * @param currentBridgeConflicts [BridgeOpeningConflictDto]
     * @return
     */
    private fun sanitize(currentBridgeConflicts: BridgeOpeningConflictDto?): BridgeOpeningConflictDto {
        return BridgeOpeningConflictDto(
            message = currentBridgeConflicts!!.message,
            relatedOpeningTimes =
            currentBridgeConflicts.relatedOpeningTimes
                .stream()
                .sorted { ot1: BridgeOpeningTimeDto, ot2: BridgeOpeningTimeDto ->
                    if (ot1.openingTime
                            .isAfter(ot2.openingTime)
                    ) 1 else 0
                }
                .distinct()
                .collect(Collectors.toList()))
    }

    private fun hasConflicts(opening1: BridgeOpeningTimeDto, opening2: BridgeOpeningTimeDto): Boolean {
        return hasConflictsByOrder(opening1, opening2) || hasConflictsByOrder(opening2, opening1)
    }

    private fun hasConflictsByOrder(opening1: BridgeOpeningTimeDto, opening2: BridgeOpeningTimeDto): Boolean {
        return if (opening1.bridge
            == opening2.bridge
        ) {
            if (opening1.closingTime
                    .isAfter(opening2.openingTime) && opening1.openingTime
                    .isBefore(opening2.openingTime)
            ) {
                true
            } else opening1.openingTime
                .isBefore(opening2.openingTime) && opening1.closingTime
                .isAfter(opening2.closingTime)
        } else false
    }
}