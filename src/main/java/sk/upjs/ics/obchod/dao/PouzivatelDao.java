package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface PouzivatelDao {
    
    List<Pouzivatel> dajPouzivatelov();
    
    Pouzivatel dajPouzivatela(Long id);
    
    Pouzivatel dajPouzivatela(String meno);
    
    void pridajPouzivatela(Pouzivatel pouzivatel);
    
    void odstranPouzivatela(Pouzivatel pouzivatel);
    
}
