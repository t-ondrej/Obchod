package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikManager {
    
    public boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik);
    
    public boolean odoberTovarZKosika(Tovar tovar);
}
