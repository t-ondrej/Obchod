package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;

public interface KosikDao {
    
    List<Kosik> dajKosiky();
    
    Kosik dajKosikPodlaId(Long idKosika);
    
    void pridajKosik(Kosik kosik);
    
    void odstranKosik(Kosik kosik);
}
