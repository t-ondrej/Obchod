
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;

public interface KategoriaDao {
    
    public List<Kategoria> dajKategorie();
    
    public Kategoria najdiPodlaId(Long id);
    
    public void uloz(Kategoria kategoria);
    
}
