package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikManager {
    
    boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik);
    
    void odoberTovarZKosika(Tovar tovar, Kosik kosik);
}
