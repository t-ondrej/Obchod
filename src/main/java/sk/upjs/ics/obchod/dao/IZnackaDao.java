
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Znacka;

public interface IZnackaDao {
    
    List<Znacka> dajZnacky();
    
    Znacka najdiPodlaId(Long id_Znacka);
    
    Znacka najdiPodlaNazvu(String nazovZnacky);
    
    Long uloz(Znacka znacka);
    
    void odstranZnacku(Znacka znacka);
    
}
