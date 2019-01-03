package com.example.dantczak.got.model.uzytkownik;


import com.example.dantczak.got.model.Wedrowka.Wedrowka;

import java.util.List;

public class Turysta extends Uzytkownik
{
    private Integer zgromadzonePunkty;
    private List<Wedrowka> wedrowki;

    public Integer getZgromadzonePunkty()
    {
        return zgromadzonePunkty;
    }

    public void setZgromadzonePunkty(Integer zgromadzonePunkty)
    {
        this.zgromadzonePunkty = zgromadzonePunkty;
    }

    public List<Wedrowka> getWedrowki()
    {
        return wedrowki;
    }

    @Override
    public boolean equals(Object obj)
    {
        return getId().equals(((Turysta)obj).getId());
    }


    public void setWedrowki(List<Wedrowka> wedrowki) {
        this.wedrowki = wedrowki;
    }
}
