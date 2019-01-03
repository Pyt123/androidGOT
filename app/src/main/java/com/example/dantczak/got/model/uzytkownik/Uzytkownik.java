package com.example.dantczak.got.model.uzytkownik;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Turysta.class, name = "Turysta"),
        @JsonSubTypes.Type(value = Przodownik.class, name = "Przodownik")
})
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
}
