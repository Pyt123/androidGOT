package com.example.dantczak.got.Activities.wedrowkawizard;

import com.example.dantczak.got.DTO.wedrowkawizard.TrasaPunktowanaLite;
import com.example.dantczak.got.DTO.wedrowkawizard.WedrowkaLite;

public class CreateWedrowkaSessionHolder {

    private static volatile CreateWedrowkaSessionHolder instance = new CreateWedrowkaSessionHolder();
    private WedrowkaLite wedrowkaLite;

    //private constructor.
    private CreateWedrowkaSessionHolder(){}

    public static CreateWedrowkaSessionHolder getInstance() {
        return instance;
    }

    public CreateWedrowkaSessionHolder createWedrowka()
    {
        wedrowkaLite= new WedrowkaLite();
        return instance;
    }
    public void addTrasa(TrasaPunktowanaLite trasa)
    {
        wedrowkaLite.addTrasa(trasa);
    }
    public WedrowkaLite getWedrowkaLite()
    {
        return wedrowkaLite;
    }

}
