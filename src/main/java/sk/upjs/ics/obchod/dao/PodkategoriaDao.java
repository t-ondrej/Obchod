
package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Podkategoria;


public interface PodkategoriaDao {
    
    public List<Podkategoria> dajPodkategorie();
    
    public Podkategoria najdiPodlaId(Long id_Podkategoria);
    
    public void uloz(Podkategoria podkategoria);
    
}
