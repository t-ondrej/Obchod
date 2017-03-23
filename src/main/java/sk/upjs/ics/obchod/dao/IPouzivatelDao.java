package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface IPouzivatelDao {
    
    List<Pouzivatel> dajPouzivatelov();
    
    Pouzivatel dajPouzivatela(Long id);
    
    Pouzivatel dajPouzivatela(String meno);
    
    Long ulozPouzivatela(Pouzivatel pouzivatel);
    
    void odstranPouzivatela(Pouzivatel pouzivatel);
    
    void novePoslednePrihlasenie(Pouzivatel pouzivatel);
    
}
