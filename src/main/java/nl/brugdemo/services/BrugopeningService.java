package nl.brugdemo.services;

import nl.brugdemo.BrugopeningConflict;
import nl.brugdemo.data.BrugopeningDto;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BrugopeningService {


    private List<BrugopeningDto> openingTimes;

    BrugopeningService(List<BrugopeningDto> openingTimes) {
        this.openingTimes = openingTimes;
    }

    public Map<String, List<BrugopeningConflict>> detectAllConflicts() {
        Map<String, List<BrugopeningConflict>> allConflicts = new HashMap<>();
        Generator.combination(openingTimes)
            .simple(2)
            .stream()
            .forEach(comb -> {
                BrugopeningDto opening1 = comb.get(0);
                BrugopeningDto opening2 = comb.get(1);
                BrugopeningConflict conflicts = findConflicts(opening1, opening2);
                if (conflicts != null) {
                    String bridgeName = opening1.getBrugNaam();
                    List<BrugopeningConflict> currentBridgeConflicts = allConflicts.get(bridgeName);
                    if (Objects.isNull(currentBridgeConflicts)) {
                        currentBridgeConflicts = new ArrayList<>();
                    }
                    currentBridgeConflicts.add(conflicts);
                    allConflicts.put(bridgeName, currentBridgeConflicts);
                }
            });
        return allConflicts;
    }

    private BrugopeningConflict findConflicts(BrugopeningDto opening1, BrugopeningDto opening2) {
        if (opening1.getBrugNaam().equalsIgnoreCase(opening2.getBrugNaam())) {
            if (opening1.getGaatDicht().isAfter((opening2.getGaatOpen())) && opening1.getGaatOpen().isBefore(opening2.getGaatOpen())) {
                return createConflict(opening1, opening2);
            } else if (opening1.getGaatOpen().isBefore(opening2.getGaatOpen()) && opening1.getGaatDicht().isAfter(opening2.getGaatDicht())) {
                return createConflict(opening1, opening2);
            }
        }
        return null;
    }

    private BrugopeningConflict createConflict(BrugopeningDto opening1, BrugopeningDto opening2) {
        BrugopeningConflict brugopeningConflict = new BrugopeningConflict();
        addOpenings(brugopeningConflict, opening1, opening2);
        return brugopeningConflict;
    }

    private void addOpenings(BrugopeningConflict brugopeningConflict, BrugopeningDto opening1, BrugopeningDto opening2) {
        List<BrugopeningDto> betrokkenElementen = brugopeningConflict.getBetrokkenElementen();
        if (betrokkenElementen == null) {
            betrokkenElementen = new ArrayList<>();
            brugopeningConflict.setBetrokkenElementen(betrokkenElementen);
        }
        betrokkenElementen.add(opening1);
        betrokkenElementen.add(opening2);
    }
}
