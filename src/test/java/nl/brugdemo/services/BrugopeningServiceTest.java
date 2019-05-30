package nl.brugdemo.services;

import static org.junit.Assert.assertEquals;

import nl.brugdemo.BrugopeningConflict;
import nl.brugdemo.data.BrugopeningDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrugopeningServiceTest {

    private BrugopeningService brugopeningService;


    @Before
    public void setUp() {
        List<BrugopeningDto> testCases = new ArrayList<>();
        BrugopeningDto brugopeningDto1 = new BrugopeningDto();
        brugopeningDto1.setBrugNaam("brug1");
        brugopeningDto1.setGaatOpen(LocalDateTime.of(2016, 11, 1, 10, 10, 0));
        brugopeningDto1.setGaatDicht(LocalDateTime.of(2016, 11, 1, 12, 10, 0));
        testCases.add(brugopeningDto1);
        BrugopeningDto brugopeningDto2 = new BrugopeningDto();
        brugopeningDto2.setGaatOpen(LocalDateTime.of(2016, 11, 1, 11, 10, 0));
        brugopeningDto2.setGaatDicht(LocalDateTime.of(2016, 11, 1, 13, 10, 0));
        brugopeningDto2.setBrugNaam("brug1");
        testCases.add(brugopeningDto2);
        this.brugopeningService = new BrugopeningService(testCases);
    }

    @Test
    public void getAllConfilcts() {
        Map<String, List<BrugopeningConflict>> brugopeningConflicts = brugopeningService.detectAllConflicts();
        assertEquals(brugopeningConflicts.size(), 1);
        assertEquals(brugopeningConflicts.get("brug1").size(), 1);
    }
}