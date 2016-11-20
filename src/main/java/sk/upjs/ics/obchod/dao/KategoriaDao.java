
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;

public interface KategoriaDao {
    
    List<Kategoria> dajKategorie();
    
    Kategoria najdiPodlaId(Long idKategoria);
    
    void uloz(Kategoria kategoria);
    
}
