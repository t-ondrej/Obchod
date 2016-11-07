package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;

public interface PouzivatelDAO {
    
    public List<Tovar> dajPouzivatelov();
    
    public void pridajPouzivatela(Pouzivatel Pouzivatel);
    
    public void odstranPouzivatela(Pouzivatel Pouzivatel);
    
    public void aktualizujPouzivatelov();
}
