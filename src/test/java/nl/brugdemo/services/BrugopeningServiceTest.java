package nl.brugdemo.services;


import static org.assertj.core.api.Assertions.assertThat;

import nl.brugdemo.BrugopeningConflict;
import nl.brugdemo.data.BrugopeningDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BrugopeningServiceTest {

    private BrugopeningService brugopeningService;

    private final BrugopeningDto brugopeningDto1 = new BrugopeningDto();
    private final BrugopeningDto brugopeningDto2 = new BrugopeningDto();
    private final BrugopeningDto brugopeningDto3 = new BrugopeningDto();

    @Before
    public void setUp() {
        List<BrugopeningDto> testCases = new ArrayList<>();
        brugopeningDto1.setBrugNaam("brug1");
        brugopeningDto1.setGaatOpen(LocalDateTime.of(2016, 11, 1, 10, 10, 0));
        brugopeningDto1.setGaatDicht(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        testCases.add(brugopeningDto1);
        brugopeningDto2.setGaatOpen(LocalDateTime.of(2016, 11, 1, 11, 10, 0));
        brugopeningDto2.setGaatDicht(LocalDateTime.of(2016, 11, 1, 13, 10, 0));
        brugopeningDto2.setBrugNaam("brug1");
        testCases.add(brugopeningDto2);
        brugopeningDto3.setGaatOpen(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        brugopeningDto3.setGaatDicht(LocalDateTime.of(2016, 11, 1, 14, 10, 0));
        brugopeningDto3.setBrugNaam("brug1");
        testCases.add(brugopeningDto3);
        this.brugopeningService = new BrugopeningService(testCases);
    }

    @Test
    public void getAllConfilcts() {
        final Map<String, Map<BrugopeningDto, BrugopeningConflict>> brugopeningConflicts = brugopeningService.detectAllConflicts();

        assertThat(brugopeningConflicts).hasSize(1);
        final Map<BrugopeningDto, BrugopeningConflict> brug1 = brugopeningConflicts.get("brug1");
        assertThat(brug1).isNotNull();
        final Set<BrugopeningDto> brugopeningDtos = brug1.keySet();
        final Object[] allOpenings = brugopeningDtos.toArray();
        assertThat(allOpenings).hasSize(2);
        assertThat(allOpenings).containsExactly(brugopeningDto1, brugopeningDto2);
        final List<BrugopeningDto> betrokkenElementenOpening1 = brug1.get(allOpenings[0]).getBetrokkenElementen();
        assertThat(betrokkenElementenOpening1).hasSize(2);
        assertThat(betrokkenElementenOpening1).containsExactly(brugopeningDto1, brugopeningDto2);
        final List<BrugopeningDto> betrokkenElementenOpening2 = brug1.get(allOpenings[1]).getBetrokkenElementen();
        assertThat(betrokkenElementenOpening2).hasSize(2);
        assertThat(betrokkenElementenOpening2).containsExactly(brugopeningDto2, brugopeningDto3);

    }
}