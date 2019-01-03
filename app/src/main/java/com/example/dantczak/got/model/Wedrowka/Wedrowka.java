package com.example.dantczak.got.model.Wedrowka;

import java.util.Date;
import java.util.List;

public class Wedrowka
{
    private Long id;
    private Long turystaId;
    private String nazwaWedrowki;
    private Date dataWedrowki;
    private Integer punktyZaWedrowke;
    private List<TrasaSkladowa> trasySkladowe;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getTurystaId()
    {
        return turystaId;
    }

    public void setTurystaId(Long turystaId)
    {
        this.turystaId = turystaId;
    }

    public String getNazwaWedrowki()
    {
        return nazwaWedrowki;
    }

    public void setNazwaWedrowki(String nazwaWedrowki)
    {
        this.nazwaWedrowki = nazwaWedrowki;
    }

    public Date getDataWedrowki()
    {
        return dataWedrowki;
    }

    public void setDataWedrowki(Date dataWedrowki)
    {
        this.dataWedrowki = dataWedrowki;
    }

    public Integer getPunktyZaWedrowke()
    {
        return punktyZaWedrowke;
    }

    public void setPunktyZaWedrowke(Integer punktyZaWedrowke)
    {
        this.punktyZaWedrowke = punktyZaWedrowke;
    }

    public List<TrasaSkladowa> getTrasySkladowe()
    {
        return trasySkladowe;
    }
}
