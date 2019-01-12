package com.example.dantczak.got.DTO.ulozwedrowke;

public class PunktTrasyDTO {
    private Long punktId;
    private String nazwaPunktu;
    private String nazwaGrupy;

    public PunktTrasyDTO(Long punktId, String nazwaPunktu, String nazwaGrupy) {
        this.punktId = punktId;
        this.nazwaPunktu = nazwaPunktu;
        this.nazwaGrupy = nazwaGrupy;
    }

    public PunktTrasyDTO() {}

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


