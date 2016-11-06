package sk.upjs.ics.obchod;

import java.util.List;

public interface PouzivatelDAO {
    
    public List<Tovar> dajPouzivatelov();
    
    public void pridajPouzivatela(Pouzivatel Pouzivatel);
    
    public void odstranPouzivatela(Pouzivatel Pouzivatel);
    
    public void aktualizujPouzivatelov();
}
