package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;

public interface KosikDao {
    
    public List<Kosik> dajKosiky();
    
    public Kosik dajKosikPodlaId(Long idKosika);
    
    public void pridajKosik(Kosik kosik);
    
    public void odstranKosik(Kosik kosik);
}
