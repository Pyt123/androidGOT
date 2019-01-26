package com.example.dantczak.got.DTO.wedrowkawizard;

public class PunktTrasyLite {
    private Long punktId;
    private String nazwaPunktu;
    private String nazwaGrupy;

    public PunktTrasyLite(Long punktId, String nazwaPunktu, String nazwaGrupy) {
        this.punktId = punktId;
        this.nazwaPunktu = nazwaPunktu;
        this.nazwaGrupy = nazwaGrupy;
    }

    public PunktTrasyLite() {}

    public Long getPunktId() {
        return punktId;
    }

    public void setPunktId(Long punktId) {
        this.punktId = punktId;
    }

    public String getNazwaPunktu() {
        return nazwaPunktu;
    }

    public void setNazwaPunktu(String nazwaPunktu) {
        this.nazwaPunktu = nazwaPunktu;
    }

    public String getNazwaGrupy() {
        return nazwaGrupy;
    }

    public void setNazwaGrupy(String nazwaGrupy) {
        this.nazwaGrupy = nazwaGrupy;
    }

    @Override
    public String toString()
    {
        return nazwaPunktu+ " : " + nazwaGrupy;
    }

}


