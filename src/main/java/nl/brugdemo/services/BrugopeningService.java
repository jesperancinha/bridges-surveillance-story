package nl.brugdemo.services;

import nl.brugdemo.BrugopeningConflict;
import nl.brugdemo.data.BrugopeningDto;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BrugopeningService {


    private List<BrugopeningDto> openingTimes;

    BrugopeningService(List<BrugopeningDto> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Map<String, Map<BrugopeningDto, BrugopeningConflict>> detectAllConflicts() {
        Map<String, Map<BrugopeningDto, BrugopeningConflict>> allConflicts = new HashMap<>();
        Generator.combination(openingTimes)
            .simple(2)
            .stream()
            .forEach(comb -> {
                BrugopeningDto opening1 = comb.get(0);
                BrugopeningDto opening2 = comb.get(1);
                if (hasConflicts(opening1, opening2)) {
                    String bridgeName = opening1.getBrugNaam();
                    Map<BrugopeningDto, BrugopeningConflict> currentBridgeConflicts = allConflicts.get(bridgeName);
                    if (Objects.isNull(currentBridgeConflicts)) {
                        currentBridgeConflicts = new HashMap<>();
                    }
                    BrugopeningConflict brugopeningConflict = currentBridgeConflicts.get(opening1);
                    if(Objects.isNull(brugopeningConflict)){
                        brugopeningConflict = new BrugopeningConflict();
                        brugopeningConflict.setBetrokkenElementen(new ArrayList<>());
                        currentBridgeConflicts.put(opening1, brugopeningConflict);
                    }
                    brugopeningConflict.getBetrokkenElementen().addAll(Arrays.asList(opening1, opening2));
                    sanitize(brugopeningConflict);
                    allConflicts.put(bridgeName, currentBridgeConflicts);
                }
            });
        return allConflicts;
    }

    /**
     * This method prevents conflictual opening times to be
     * @param currentBridgeConflicts
     */
    private void sanitize(BrugopeningConflict currentBridgeConflicts) {
        currentBridgeConflicts
            .setBetrokkenElementen(currentBridgeConflicts.getBetrokkenElementen()
                .stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    private boolean hasConflicts(BrugopeningDto opening1, BrugopeningDto opening2) {
        if (opening1.getBrugNaam().equalsIgnoreCase(opening2.getBrugNaam())) {
            if (opening1.getGaatDicht().isAfter((opening2.getGaatOpen())) && opening1.getGaatOpen().isBefore(opening2.getGaatOpen())) {
                return true;
            } else
                return opening1.getGaatOpen().isBefore(opening2.getGaatOpen()) && opening1.getGaatDicht().isAfter(opening2.getGaatDicht());
        }
        return false;
    }

}
