package nl.brugdemo;

import nl.brugdemo.data.BrugopeningDto;

import java.util.List;

/**
 * Dit object wordt gemaakt wanneer er een conflict is tussen brugopeningen.
 */
public class BrugopeningConflict {

    private String melding;
    private List<BrugopeningDto> betrokkenElementen;

    public String getMelding() {
        return melding;
    }

    public void setMelding(String melding) {
        this.melding = melding;
    }

    public List<BrugopeningDto> getBetrokkenElementen() {
        return betrokkenElementen;
    }

    public void setBetrokkenElementen(List<BrugopeningDto> betrokkenElementen) {
        this.betrokkenElementen = betrokkenElementen;
    }
}
