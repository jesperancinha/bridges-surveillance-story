package nl.brugdemo;

import java.util.List;

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
