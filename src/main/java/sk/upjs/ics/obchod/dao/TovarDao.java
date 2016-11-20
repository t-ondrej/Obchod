package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public interface TovarDao {
    
    List<Tovar> dajTovar();
    
    List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria);
    
    List<Tovar> dajTovarPodlaNazvu(String nazov);
    
    List<Tovar> dajTovarPodlaZnacky(Znacka znacka);
    
    void pridajTovar(Tovar tovar);
    
    void odstranTovar(Tovar tovar);
          
}
