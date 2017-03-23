package sk.upjs.ics.obchod.managers;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Tovar;

public interface IKosikManager {
    
    boolean pridajTovarDoKosika(Tovar tovar);
    
    void odoberTovarZKosika(Tovar tovar);
    
    List<Tovar> dajTovarKosika();
    
    void vyprazdniKosik();
    
    int dajPocetTovaruVKosiku(Tovar tovar);
    
    IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar);
    
    ObservableList<Tovar> tovarKosikaObservableList();
    
}
