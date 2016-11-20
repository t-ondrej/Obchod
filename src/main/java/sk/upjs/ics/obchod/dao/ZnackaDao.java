
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Znacka;

public interface ZnackaDao {
    
    List<Znacka> dajZnacky();
    
    Znacka najdiPodlaId(Long id_Znacka);
    
    void uloz(Znacka znacka);
    
}
