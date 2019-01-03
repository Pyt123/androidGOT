package com.example.dantczak.got.model.uzytkownik;

public abstract class Uzytkownik
{
    private Long id;
    private String imie;
    private String nazwisko;

    public Long getId()
    {
        return id;
    }

    public String getImie()
    {
        return imie;
    }

    public String getNazwisko()
    {
        return nazwisko;
    }
}
