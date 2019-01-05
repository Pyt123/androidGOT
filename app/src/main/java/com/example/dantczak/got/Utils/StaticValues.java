package com.example.dantczak.got.Utils;

import com.example.dantczak.got.model.grupa.GrupaGorska;
import com.example.dantczak.got.model.uzytkownik.Przodownik;
import com.example.dantczak.got.model.uzytkownik.Turysta;

import java.util.ArrayList;
import java.util.List;

public class StaticValues
{
    private static List<GrupaGorska> authorizedGroups = new ArrayList<GrupaGorska>(){{
        add(new GrupaGorska(1L, "Tatry"));
    }};
    public static Przodownik loggedInPrzodownik = new Przodownik(1L, authorizedGroups);

    public static Turysta loggedInTurysta = new Turysta() {{setId(1L);}};
}
