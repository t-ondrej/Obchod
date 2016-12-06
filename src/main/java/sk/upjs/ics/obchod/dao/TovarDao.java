package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public interface TovarDao {
    
    List<Tovar> dajTovary();
    
    List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria);
    
    Tovar dajTovarPodlaNazvu(String nazov);
    
    List<Tovar> dajTovarPodlaZnacky(Znacka znacka);
    
    Tovar najdiPodlaId(Long id);
    
    int dajPocetTovaru(Tovar tovar);
    
    void nastavTovaruPocetKusov(Tovar tovar, int pocet);    
    
    Long ulozTovar(Tovar tovar);
    
    void odstranTovar(Tovar tovar);   
          
}
