package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikManager {
    
    boolean pridajTovarDoKosika(Tovar tovar);
    
    void odoberTovarZKosika(Tovar tovar);
    
    List<Tovar> dajTovaryKosika();
    
    void vyprazdniKosik();
    
    int dajPocetTovaruVKosiku(Tovar tovar);
    
    IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar);
    
    ObservableList<Tovar> tovarKosikaObservableList();
    
}
