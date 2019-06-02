package com.jesperancinha.bridgelogistics.services;

import com.jesperancinha.bridgelogistics.data.BridgeOpeningConflictDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningDto;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This service manages the bridge opening times service. It detects conflict and handles main bridge functions
 */
public class BridgeOpeningService {


    private Map<String, List<BridgeOpeningDto>> openingTimes;

    /**
     * Initialize your service with an opening times map per bridge name
     *
     * @param openingTimes {@link Map}
     */
    BridgeOpeningService(Map<String, List<BridgeOpeningDto>> openingTimes) {
        this.openingTimes = openingTimes;

    }

    /**
     * Initialize your service with an unsorted opening times list
     *
     * @param allOpeningTimes {@link List}
     */
    BridgeOpeningService(List<BridgeOpeningDto> allOpeningTimes) {
        this.openingTimes = allOpeningTimes.stream().collect(
            Collectors.groupingBy(BridgeOpeningDto::getBridgeName));
    }

    /**
     * Detects all conflictual time slots in current bridge times
     *
     * @return All conflicts map per bridge, per opening times {@link Map}
     */
    public Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> detectAllConflicts() {
        Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> allConflicts = new HashMap<>();
        openingTimes.forEach((bridgeName, bridgeOeningTimes) ->
            Generator.combination(bridgeOeningTimes)
                .simple(2)
                .stream()
                .map(combination -> Generator.permutation(combination).simple())
                .flatMap(IGenerator::stream)
                .forEach(comb -> findConflictInATwoListBridgeOpeningTimes(allConflicts, comb)));

        return allConflicts;
    }

    private void findConflictInATwoListBridgeOpeningTimes(Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> allConflicts, List<BridgeOpeningDto> comb) {
        BridgeOpeningDto opening1 = comb.get(0);
        BridgeOpeningDto opening2 = comb.get(1);
        if (hasConflicts(opening1, opening2)) {
            handleConflicts(allConflicts, opening1, opening2);
        }
    }

    private void handleConflicts(Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> allConflicts, BridgeOpeningDto opening1, BridgeOpeningDto opening2) {
        String bridgeName = opening1.getBridgeName();
        Map<BridgeOpeningDto, BridgeOpeningConflictDto> currentBridgeConflicts = createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(allConflicts, bridgeName);
        BridgeOpeningConflictDto bridgeOpeningConflictDto = createOrGetBridgeOpeningConflict(currentBridgeConflicts, opening1);
        bridgeOpeningConflictDto.getRelatedOpeningTimes().addAll(Arrays.asList(opening1, opening2));
        sanitize(bridgeOpeningConflictDto);
        allConflicts.put(bridgeName, currentBridgeConflicts);
    }

    private BridgeOpeningConflictDto createOrGetBridgeOpeningConflict(Map<BridgeOpeningDto, BridgeOpeningConflictDto> currentBridgeConflicts, BridgeOpeningDto opening1) {
        BridgeOpeningConflictDto bridgeOpeningConflictDto = currentBridgeConflicts.get(opening1);
        if (Objects.isNull(bridgeOpeningConflictDto)) {
            bridgeOpeningConflictDto = new BridgeOpeningConflictDto();
            bridgeOpeningConflictDto.setRelatedOpeningTimes(new ArrayList<>());
            currentBridgeConflicts.put(opening1, bridgeOpeningConflictDto);
        }
        return bridgeOpeningConflictDto;
    }

    private Map<BridgeOpeningDto, BridgeOpeningConflictDto> createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(Map<String, Map<BridgeOpeningDto, BridgeOpeningConflictDto>> allConflicts, String bridgeName) {
        Map<BridgeOpeningDto, BridgeOpeningConflictDto> currentBridgeConflicts = allConflicts.get(bridgeName);
        if (Objects.isNull(currentBridgeConflicts)) {
            currentBridgeConflicts = new HashMap<>();
        }
        return currentBridgeConflicts;
    }

    /**
     * This method prevents conflictual opening times to be
     *
     * @param currentBridgeConflicts {@link BridgeOpeningConflictDto}
     */
    private void sanitize(BridgeOpeningConflictDto currentBridgeConflicts) {
        currentBridgeConflicts
            .setRelatedOpeningTimes(currentBridgeConflicts.getRelatedOpeningTimes()
                .stream()
                .sorted((ot1, ot2) -> ot1.getOpeningTime().isAfter(ot2.getOpeningTime()) ? 1 : 0)
                .distinct()
                .collect(Collectors.toList()));
    }

    private boolean hasConflicts(BridgeOpeningDto opening1, BridgeOpeningDto opening2) {
        return hasConflictsByOrder(opening1, opening2) || hasConflictsByOrder(opening2, opening1);
    }

    private boolean hasConflictsByOrder(BridgeOpeningDto opening1, BridgeOpeningDto opening2) {
        if (opening1.getBridgeName().equalsIgnoreCase(opening2.getBridgeName())) {
            if (opening1.getClosingTime().isAfter((opening2.getOpeningTime())) && opening1.getOpeningTime().isBefore(opening2.getOpeningTime())) {
                return true;
            } else
                return opening1.getOpeningTime().isBefore(opening2.getOpeningTime()) && opening1.getClosingTime().isAfter(opening2.getClosingTime());
        }
        return false;
    }

}
