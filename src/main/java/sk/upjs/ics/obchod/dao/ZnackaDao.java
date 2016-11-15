
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Znacka;

public interface ZnackaDao {
    
    public List<Znacka> dajZnacky();
    
    public Znacka najdiPodlaId(Long id_Znacka);
    
    public void uloz(Znacka znacka);
    
}
