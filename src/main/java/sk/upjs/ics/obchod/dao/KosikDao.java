package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;

public interface KosikDao {
    
    List<Kosik> dajKosiky();
    
    Kosik dajKosikPodlaId(Long idKosika);
    
    Long pridajKosikVratId(Kosik kosik);
    
    void odstranKosik(Kosik kosik);
}
