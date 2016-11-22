package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikDao {
    
    List<Kosik> dajKosiky();
    
    Kosik dajKosikPodlaId(Long idKosika);
    
    Long pridajKosikVratId(Kosik kosik);
    
    void odstranKosik(Kosik kosik);
    
    void dajTovarDoKosika(Long idTovaru ,Long idKosika);
    
    List<Tovar> dajTovaryKosika(Long idKosika);
            
}
