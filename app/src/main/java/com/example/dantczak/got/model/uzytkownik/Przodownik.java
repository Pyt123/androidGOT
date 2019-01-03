package com.example.dantczak.got.model.uzytkownik;

import com.example.dantczak.got.model.grupa.GrupaGorska;
import java.util.List;

public class Przodownik extends Uzytkownik
{
    private List<GrupaGorska> authorizedGrupy;

    public List<GrupaGorska> getAuthorizedGrupy()
    {
        return authorizedGrupy;
    }

    @Override
    public boolean equals(Object obj)
    {
        return getId().equals(((Przodownik)obj).getId());
    }

    public void setAuthorizedGrupy(List<GrupaGorska> authorizedGrupy) {
        this.authorizedGrupy = authorizedGrupy;
    }
}
