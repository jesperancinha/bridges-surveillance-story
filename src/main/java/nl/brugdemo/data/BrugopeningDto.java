package nl.brugdemo.data;

import java.time.LocalDateTime;

/**
 * Er zijn diverse spoorbruggen in Nederland die open en dicht kunnen.
 * Dit object stelt een openings en sluitingstijd van een brug voor.
 */
public class BrugopeningDto {

    private String brugNaam;
    private LocalDateTime gaatOpen;
    private LocalDateTime gaatDicht;

    public String getBrugNaam() {
        return brugNaam;
    }

    public void setBrugNaam(String brugNaam) {
        this.brugNaam = brugNaam;
    }

    public LocalDateTime getGaatOpen() {
        return gaatOpen;
    }

    public void setGaatOpen(LocalDateTime gaatOpen) {
        this.gaatOpen = gaatOpen;
    }

    public LocalDateTime getGaatDicht() {
        return gaatDicht;
    }

    public void setGaatDicht(LocalDateTime gaatDicht) {
        this.gaatDicht = gaatDicht;
    }
}
