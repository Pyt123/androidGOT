package com.example.dantczak.got.model.trasa;

import com.example.dantczak.got.model.grupa.GrupaGorska;

import java.util.Date;
import java.util.List;

/**
 * Created by Cinek on 27.12.2018.
 */
public class TrasaPunktowana extends Trasa {
    private String nazwa;
    private Integer liczbaPunktow;
    private Date dataDodania;
    private Date dataUsuniecia;
    private Long poprzedniaWersjaId;

    public TrasaPunktowana() {}

    public TrasaPunktowana(Long id, String nazwa, List<SkladowyPunktTrasy> skladowePunktyTrasy, GrupaGorska grupaGorska, Integer liczbaPunktow, Date dataDodania, Date dataUsuniecia) {
        super(id, skladowePunktyTrasy, grupaGorska);
        this.nazwa = nazwa;
        this.liczbaPunktow = liczbaPunktow;
        this.dataDodania = dataDodania;
        this.dataUsuniecia = dataUsuniecia;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setLiczbaPunktow(Integer liczbaPunktow) {
        this.liczbaPunktow = liczbaPunktow;
    }

    public void setDataDodania(Date dataDodania) {
        this.dataDodania = dataDodania;
    }

    public void setDataUsuniecia(Date dataUsuniecia) {
        this.dataUsuniecia = dataUsuniecia;
    }

    public Long getPoprzedniaWersjaId() {
        return poprzedniaWersjaId;
    }

    public void setPoprzedniaWersjaId(Long poprzedniaWersjaId) {
        this.poprzedniaWersjaId = poprzedniaWersjaId;
    }

    @Override
    public int getLiczbaPunktow() {
        return liczbaPunktow;
    }
    public Date getDataDodania() {
        return dataDodania;
    }
    public Date getDataUsuniecia() {
        return dataUsuniecia;
    }
    public void setLiczbaPunktow(int liczbaPunktow) {
        this.liczbaPunktow = liczbaPunktow;
    }
}
