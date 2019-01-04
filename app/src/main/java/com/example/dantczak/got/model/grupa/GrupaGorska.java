package com.example.dantczak.got.model.grupa;

import android.util.Log;

import java.util.Comparator;

/**
 * Created by Cinek on 27.12.2018.
 */
public class GrupaGorska implements Comparator<Object> {
    private Long id;
    private String nazwaGrupy;

    public GrupaGorska(){}

    public GrupaGorska(Long id, String nazwaGrupy) {
        this.id = id;
        this.nazwaGrupy = nazwaGrupy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwaGrupy() {
        return nazwaGrupy;
    }

    public void setNazwaGrupy(String nazwaGrupy) {
        this.nazwaGrupy = nazwaGrupy;
    }

    @Override
    public int compare(Object o2, Object o1)
    {
        GrupaGorska gg = (GrupaGorska)o1;
        Long id2;
        try
        {
            id2 = (Long)o2;
        }
        catch (ClassCastException e)
        {
            id2 = ((GrupaGorska)o2).id;
        }

        Long ret = gg.getId() - id2;
        if(ret > 0) { return 1; }
        else if (ret < 0) { return -1; }
        else return 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        try
        {
            return id.equals(((GrupaGorska)obj).id);
        }
        catch (ClassCastException e)
        {
            return id.equals(obj);
        }
    }
}
