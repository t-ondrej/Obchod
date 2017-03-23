package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;

public interface IKategoriaManager {
    
    List<Kategoria> dajKategorie();
    
    void odstranKategoriu(Kategoria kategoria);
    
    void upravKategoriu(Kategoria kategoria, String nazov);
    
    Long pridajKategoriu(Kategoria kategoria);
    
    boolean existujeKategoriaSNazvom(String nazov);
    
    boolean existujeTovarVKategorii(Kategoria kategoria);
}
