package com.example.dantczak.got.model.grupa;

/**
 * Created by Cinek on 27.12.2018.
 */
public class GrupaGorska {
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
    public boolean equals(Object obj)
    {
        return id.equals(((GrupaGorska)obj).id);
    }
}
