package com.example.dantczak.got.DTO.wedrowkawizard;

public class TrasaPunktowanaLite {
    private Long idTrasy;
    private String nazwaTrasy;
    private Integer liczbaPunktow;
    private Float distance;

    private PunktTrasyLite punktPoczatkowy;
    private PunktTrasyLite punktKoncowy;


    public TrasaPunktowanaLite(Long idTrasy, String nazwaTrasy, Integer liczbaPunktow, Float distance, PunktTrasyLite punktPoczatkowy, PunktTrasyLite punktKoncowy) {
        this.idTrasy = idTrasy;
        this.nazwaTrasy = nazwaTrasy;
        this.liczbaPunktow = liczbaPunktow;
        this.distance = distance;
        this.punktPoczatkowy = punktPoczatkowy;
        this.punktKoncowy = punktKoncowy;
    }

    public TrasaPunktowanaLite() {
    }

    public PunktTrasyLite getPunktPoczatkowy() {
        return punktPoczatkowy;
    }

    public void setPunktPoczatkowy(PunktTrasyLite punktPoczatkowy) {
        this.punktPoczatkowy = punktPoczatkowy;
    }

    public PunktTrasyLite getPunktKoncowy() {
        return punktKoncowy;
    }

    public void setPunktKoncowy(PunktTrasyLite punktKoncowy) {
        this.punktKoncowy = punktKoncowy;
    }

    public Long getIdTrasy() {
        return idTrasy;
    }

    public void setIdTrasy(Long idTrasy) {
        this.idTrasy = idTrasy;
    }

    public String getNazwaTrasy() {
        return nazwaTrasy;
    }

    public void setNazwaTrasy(String nazwaTrasy) {
        this.nazwaTrasy = nazwaTrasy;
    }

    public Integer getLiczbaPunktow() {
        return liczbaPunktow;
    }

    public void setLiczbaPunktow(Integer liczbaPunktow) {
        this.liczbaPunktow = liczbaPunktow;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}