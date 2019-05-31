package com.jesperancinha.bridgelogistics.services;

import com.jesperancinha.bridgelogistics.BridgeOpeningConflict;
import com.jesperancinha.bridgelogistics.data.BridgeOpeningDto;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BridgeOpeningService {


    private List<BridgeOpeningDto> openingTimes;

    BridgeOpeningService(List<BridgeOpeningDto> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Map<String, Map<BridgeOpeningDto, BridgeOpeningConflict>> detectAllConflicts() {
        Map<String, Map<BridgeOpeningDto, BridgeOpeningConflict>> allConflicts = new HashMap<>();
        Generator.combination(openingTimes)
            .simple(2)
            .stream()
            .forEach(comb -> {
                BridgeOpeningDto opening1 = comb.get(0);
                BridgeOpeningDto opening2 = comb.get(1);
                if (hasConflicts(opening1, opening2)) {
                    String bridgeName = opening1.getBridgeName();
                    Map<BridgeOpeningDto, BridgeOpeningConflict> currentBridgeConflicts = allConflicts.get(bridgeName);
                    if (Objects.isNull(currentBridgeConflicts)) {
                        currentBridgeConflicts = new HashMap<>();
                    }
                    BridgeOpeningConflict bridgeOpeningConflict = currentBridgeConflicts.get(opening1);
                    if(Objects.isNull(bridgeOpeningConflict)){
                        bridgeOpeningConflict = new BridgeOpeningConflict();
                        bridgeOpeningConflict.setRelatedOpeningTimes(new ArrayList<>());
                        currentBridgeConflicts.put(opening1, bridgeOpeningConflict);
                    }
                    bridgeOpeningConflict.getRelatedOpeningTimes().addAll(Arrays.asList(opening1, opening2));
                    sanitize(bridgeOpeningConflict);
                    allConflicts.put(bridgeName, currentBridgeConflicts);
                }
            });
        return allConflicts;
    }

    /**
     * This method prevents conflictual opening times to be
     * @param currentBridgeConflicts {@link BridgeOpeningConflict}
     */
    private void sanitize(BridgeOpeningConflict currentBridgeConflicts) {
        currentBridgeConflicts
            .setRelatedOpeningTimes(currentBridgeConflicts.getRelatedOpeningTimes()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    private boolean hasConflicts(BridgeOpeningDto opening1, BridgeOpeningDto opening2) {
        if (opening1.getBridgeName().equalsIgnoreCase(opening2.getBridgeName())) {
            if (opening1.getClosingTime().isAfter((opening2.getOpeningTime())) && opening1.getOpeningTime().isBefore(opening2.getOpeningTime())) {
                return true;
            } else
                return opening1.getOpeningTime().isBefore(opening2.getOpeningTime()) && opening1.getClosingTime().isAfter(opening2.getClosingTime());
        }
        return false;
    }

}
