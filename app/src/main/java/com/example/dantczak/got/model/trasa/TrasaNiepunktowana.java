package com.example.dantczak.got.model.trasa;

import com.example.dantczak.got.model.grupa.GrupaGorska;

import java.util.List;

/**
 * Created by Cinek on 27.12.2018.
 */
public class TrasaNiepunktowana extends Trasa {
    public TrasaNiepunktowana() {}

    public TrasaNiepunktowana(Long id, List<SkladowyPunktTrasy> skladowePunktyTrasy, GrupaGorska grupaGorska) {
        super(id, skladowePunktyTrasy, grupaGorska);
    }

    @Override
    public int getLiczbaPunktow() {
        return 0;
    }
}
