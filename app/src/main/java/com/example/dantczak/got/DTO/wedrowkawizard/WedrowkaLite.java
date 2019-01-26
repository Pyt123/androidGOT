package com.example.dantczak.got.DTO.wedrowkawizard;

import java.util.ArrayList;
import java.util.List;

public class WedrowkaLite {
    private String nazwa;
    private List<TrasaSkladowa> trasySkladowe;

    public WedrowkaLite(String nazwa, List<TrasaSkladowa> trasySkladowe) {
        this.nazwa = nazwa;
        this.trasySkladowe = trasySkladowe;
    }

    public WedrowkaLite()
    {
        trasySkladowe = new ArrayList<>();

    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<TrasaSkladowa> getTrasySkladowe() {
        return trasySkladowe;
    }

    public void setTrasySkladowe(List<TrasaSkladowa> trasySkladowe) {
        this.trasySkladowe = trasySkladowe;
    }


    public float calcDistanceSum()
    {
        float sum = 0;
        for (TrasaSkladowa trasaSkladowa : trasySkladowe)
        {
            sum += trasaSkladowa.getTrasaPunktowanaLite().getDistance();
        }
        return sum;
    }
    public int calcPtsSum()
    {
        int sum = 0;
        for (TrasaSkladowa trasaSkladowa: trasySkladowe)
        {
            sum += trasaSkladowa.getTrasaPunktowanaLite().getLiczbaPunktow();
        }
        return sum;
    }
    private void addTrasaSkladowa(TrasaSkladowa trasaSkladowa)
    {
        this.trasySkladowe.add(trasaSkladowa);
    }
    public void addTrasa(TrasaPunktowanaLite trasaPunktowanaLite)
    {
        TrasaSkladowa trasaSkladowa = new TrasaSkladowa(trasaPunktowanaLite, this.trasySkladowe.size());
        addTrasaSkladowa(trasaSkladowa);
    }

}
