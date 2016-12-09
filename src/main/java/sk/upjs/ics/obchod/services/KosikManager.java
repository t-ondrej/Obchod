package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikManager {
    
    boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik);
    
    void odoberTovarZKosika(Tovar tovar, Kosik kosik);
    
    List<Tovar> dajTovaryKosika(Kosik kosik);
    
    void vyprazdniKosik(Kosik kosik);
    
    int dajPocetTovaruVKosiku(Tovar tovar, Kosik kosik);
    
    IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar, Kosik kosik);
    
    ObservableList<Tovar> tovarKosikaObservableList(Kosik kosik);
    
}
