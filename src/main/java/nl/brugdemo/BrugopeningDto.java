package nl.brugdemo;

import java.util.Date;

public class BrugopeningDto {

    private String naam;
    private Date gaatOpen;
    private Date gaatDicht;


    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getGaatOpen() {
        return gaatOpen;
    }

    public void setGaatOpen(Date gaatOpen) {
        this.gaatOpen = gaatOpen;
    }

    public Date getGaatDicht() {
        return gaatDicht;
    }

    public void setGaatDicht(Date gaatDicht) {
        this.gaatDicht = gaatDicht;
    }
}
