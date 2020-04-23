package org.jesperancinha.logistics.web.services;

import org.jesperancinha.logistics.web.data.BridgeDto;
import org.jesperancinha.logistics.web.data.BridgeOpeningConflictDto;
import org.jesperancinha.logistics.web.data.BridgeOpeningTimeDto;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;
import org.springframework.stereotype.Service;

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
@Service
public class BridgeOpeningService {

    /**
     * Initialize your service with an opening times map per bridge name
     *
     * @param openingTimes {@link Map}
     * @return
     */
    public Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> getAllConflicts(Map<BridgeDto, List<BridgeOpeningTimeDto>> openingTimes) {
        return detectAllConflicts(openingTimes);

    }

    /**
     * Initialize your service with an unsorted opening times list
     *
     * @param allOpeningTimes {@link List}
     * @return
     */
    public Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> getAllConflicts(List<BridgeOpeningTimeDto> allOpeningTimes) {
        Map<BridgeDto, List<BridgeOpeningTimeDto>> openingTimes = allOpeningTimes.stream()
            .collect(Collectors.groupingBy(BridgeOpeningTimeDto::bridge));
        return detectAllConflicts(openingTimes);
    }

    /**
     * Detects all conflictual time slots in current bridge times
     *
     * @param openingTimes
     * @return All conflicts map per bridge, per opening times {@link Map}
     */
    public Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> detectAllConflicts(Map<BridgeDto, List<BridgeOpeningTimeDto>> openingTimes) {
        Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> allConflicts = new HashMap<>();
        openingTimes.forEach((bridgeName, bridgeOeningTimes) -> Generator.combination(bridgeOeningTimes)
            .simple(2)
            .stream()
            .map(combination -> Generator.permutation(combination)
                .simple())
            .flatMap(IGenerator::stream)
            .forEach(comb -> findConflictInATwoListBridgeOpeningTimes(allConflicts, comb)));

        return allConflicts;
    }

    private void findConflictInATwoListBridgeOpeningTimes(Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> allConflicts, List<BridgeOpeningTimeDto> comb) {
        BridgeOpeningTimeDto opening1 = comb.get(0);
        BridgeOpeningTimeDto opening2 = comb.get(1);
        if (hasConflicts(opening1, opening2)) {
            handleConflicts(allConflicts, opening1, opening2);
        }
    }

    private void handleConflicts(Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> allConflicts, BridgeOpeningTimeDto opening1, BridgeOpeningTimeDto opening2) {
        BridgeDto bridge = opening1.bridge();
        Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> currentBridgeConflicts = createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(allConflicts, bridge);
        BridgeOpeningConflictDto bridgeOpeningConflictDto = createOrGetBridgeOpeningConflict(currentBridgeConflicts, opening1);
        bridgeOpeningConflictDto.relatedOpeningTimes()
            .addAll(Arrays.asList(opening1, opening2));
        if (currentBridgeConflicts.get(opening1) != null) {
            bridgeOpeningConflictDto = sanitize(bridgeOpeningConflictDto);
            currentBridgeConflicts.put(opening1, bridgeOpeningConflictDto);
        }
        allConflicts.put(bridge, currentBridgeConflicts);
    }

    private BridgeOpeningConflictDto createOrGetBridgeOpeningConflict(Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> currentBridgeConflicts, BridgeOpeningTimeDto opening1) {
        BridgeOpeningConflictDto bridgeOpeningConflictDto = currentBridgeConflicts.get(opening1);
        if (Objects.isNull(bridgeOpeningConflictDto)) {
            bridgeOpeningConflictDto = BridgeOpeningConflictDto.builder()
                .relatedOpeningTimes(new ArrayList<>())
                .build();
            currentBridgeConflicts.put(opening1, bridgeOpeningConflictDto);
        }
        return bridgeOpeningConflictDto;
    }

    private Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> createOrGetBridgeOpeningDtoBridgeOpeningConflictMap(Map<BridgeDto, Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto>> allConflicts, BridgeDto bridge) {
        Map<BridgeOpeningTimeDto, BridgeOpeningConflictDto> currentBridgeConflicts = allConflicts.get(bridge);
        if (Objects.isNull(currentBridgeConflicts)) {
            currentBridgeConflicts = new HashMap<>();
        }
        return currentBridgeConflicts;
    }

    /**
     * This method prevents conflictual opening times to be
     *
     * @param currentBridgeConflicts {@link BridgeOpeningConflictDto}
     * @return
     */
    private BridgeOpeningConflictDto sanitize(BridgeOpeningConflictDto currentBridgeConflicts) {
        return BridgeOpeningConflictDto.builder()
            .message(currentBridgeConflicts.message())
            .relatedOpeningTimes(currentBridgeConflicts.relatedOpeningTimes()
                .stream()
                .sorted((ot1, ot2) -> ot1.openingTime()
                    .isAfter(ot2.openingTime()) ?
                    1 :
                    0)
                .distinct()
                .collect(Collectors.toList()))
            .build();
    }

    private boolean hasConflicts(BridgeOpeningTimeDto opening1, BridgeOpeningTimeDto opening2) {
        return hasConflictsByOrder(opening1, opening2) || hasConflictsByOrder(opening2, opening1);
    }

    private boolean hasConflictsByOrder(BridgeOpeningTimeDto opening1, BridgeOpeningTimeDto opening2) {
        if (opening1.bridge()
            .equals(opening2.bridge())) {
            if (opening1.closingTime()
                .isAfter((opening2.openingTime())) && opening1.openingTime()
                .isBefore(opening2.openingTime())) {
                return true;
            } else
                return opening1.openingTime()
                    .isBefore(opening2.openingTime()) && opening1.closingTime()
                    .isAfter(opening2.closingTime());
        }
        return false;
    }

}
