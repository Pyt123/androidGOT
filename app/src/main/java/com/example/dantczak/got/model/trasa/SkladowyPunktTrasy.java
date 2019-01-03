package com.example.dantczak.got.model.trasa;

/**
 * Created by Cinek on 27.12.2018.
 */
public class SkladowyPunktTrasy {
    private PunktTrasy punktTrasy;
    private int kolejnoscPunktu;

    public SkladowyPunktTrasy() {}

    public SkladowyPunktTrasy(PunktTrasy punktTrasy, int kolejnoscPunktu) {
        this.punktTrasy = punktTrasy;
        this.kolejnoscPunktu = kolejnoscPunktu;
    }

    public PunktTrasy getPunktTrasy() {
        return punktTrasy;
    }

    public void setPunktTrasy(PunktTrasy punktTrasy) {
        this.punktTrasy = punktTrasy;
    }

    public int getKolejnoscPunktu() {
        return kolejnoscPunktu;
    }

    public void setKolejnoscPunktu(int kolejnoscPunktu) {
        this.kolejnoscPunktu = kolejnoscPunktu;
    }
}
