package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface PouzivatelDAO {
    
    public List<Pouzivatel> dajPouzivatelov();
    
    public void pridajPouzivatela(Pouzivatel pouzivatel);
    
    public void odstranPouzivatela(Pouzivatel pouzivatel);
    
}
