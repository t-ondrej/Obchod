package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.TovarModel;

public interface KosikManager {
    
    boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik);
    
    void odoberTovarZKosika(Tovar tovar, Kosik kosik);
    
    List<Tovar> dajTovarKosika(Kosik kosik);
}
