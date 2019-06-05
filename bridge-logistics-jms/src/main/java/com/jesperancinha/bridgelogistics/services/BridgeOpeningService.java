package com.jesperancinha.bridgelogistics.services;

import com.jesperancinha.bridgelogistics.data.BridgeDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningConflictDto;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningTimesDto;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This service manages the bridge opening times service. It detects conflict and handles main bridge functions
 */
public class BridgeOpeningService {


    private Map<BridgeDto, List<BridgeOpeningTimesDto>> openingTimes;

    /**
     * Initialize your service with an opening times map per bridge name
     *
     * @param openingTimes {@link Map}
     */
    BridgeOpeningService(Map<BridgeDto, List<BridgeOpeningTimesDto>> openingTimes) {
        this.openingTimes = openingTimes;

    }

    /**
     * Initialize your service with an unsorted opening times list
     *
     * @param allOpeningTimes {@link List}
     */
    BridgeOpeningService(List<BridgeOpeningTimesDto> allOpeningTimes) {
        this.openingTimes = allOpeningTimes.stream().collect(
                Collectors.groupingBy(BridgeOpeningTimesDto::getBridge));
    }

    /**
     * Detects all conflictual time slots in current bridge times
     *
     * @return All conflicts map per bridge, per opening times {@link Map}
     */
    public Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> detectAllConflicts() {
        Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> allConflicts = new HashMap<>();
        openingTimes.forEach((bridgeName, bridgeOeningTimes) ->
                Generator.combination(bridgeOeningTimes)
                        .simple(2)
                        .stream()
                        .map(combination -> Generator.permutation(combination).simple())
                        .flatMap(IGenerator::stream)
                        .forEach(comb -> findConflictInATwoListBridgeOpeningTimes(allConflicts, comb)));

        return allConflicts;
    }

    private void findConflictInATwoListBridgeOpeningTimes(Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> allConflicts, List<BridgeOpeningTimesDto> comb) {
        BridgeOpeningTimesDto opening1 = comb.get(0);
        BridgeOpeningTimesDto opening2 = comb.get(1);
        if (hasConflicts(opening1, opening2)) {
            handleConflicts(allConflicts, opening1, opening2);
        }
    }

    private void handleConflicts(Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> allConflicts, BridgeOpeningTimesDto opening1, BridgeOpeningTimesDto opening2) {
        BridgeDto bridge = opening1.getBridge();
        Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> currentBridgeConflicts = createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(allConflicts, bridge);
        BridgeOpeningConflictDto bridgeOpeningConflictDto = createOrGetBridgeOpeningConflict(currentBridgeConflicts, opening1);
        bridgeOpeningConflictDto.getRelatedOpeningTimes().addAll(Arrays.asList(opening1, opening2));
        sanitize(bridgeOpeningConflictDto);
        allConflicts.put(bridge, currentBridgeConflicts);
    }

    private BridgeOpeningConflictDto createOrGetBridgeOpeningConflict(Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> currentBridgeConflicts, BridgeOpeningTimesDto opening1) {
        BridgeOpeningConflictDto bridgeOpeningConflictDto = currentBridgeConflicts.get(opening1);
        if (Objects.isNull(bridgeOpeningConflictDto)) {
            bridgeOpeningConflictDto = new BridgeOpeningConflictDto();
            bridgeOpeningConflictDto.setRelatedOpeningTimes(new ArrayList<>());
            currentBridgeConflicts.put(opening1, bridgeOpeningConflictDto);
        }
        return bridgeOpeningConflictDto;
    }

    private Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(Map<BridgeDto, Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto>> allConflicts, BridgeDto bridge) {
        Map<BridgeOpeningTimesDto, BridgeOpeningConflictDto> currentBridgeConflicts = allConflicts.get(bridge);
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

    private boolean hasConflicts(BridgeOpeningTimesDto opening1, BridgeOpeningTimesDto opening2) {
        return hasConflictsByOrder(opening1, opening2) || hasConflictsByOrder(opening2, opening1);
    }

    private boolean hasConflictsByOrder(BridgeOpeningTimesDto opening1, BridgeOpeningTimesDto opening2) {
        if (opening1.getBridge().equals(opening2.getBridge())) {
            if (opening1.getClosingTime().isAfter((opening2.getOpeningTime())) && opening1.getOpeningTime().isBefore(opening2.getOpeningTime())) {
                return true;
            } else
                return opening1.getOpeningTime().isBefore(opening2.getOpeningTime()) && opening1.getClosingTime().isAfter(opening2.getClosingTime());
        }
        return false;
    }

}
