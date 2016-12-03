package sk.upjs.ics.obchod.entity;

import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Kosik {
    
    private final ObservableMap<Tovar, IntegerProperty> tovary = FXCollections.observableHashMap();
    
    private final IntegerProperty celkovaCena = new SimpleIntegerProperty(0);
    
    public ObservableMap<Tovar, IntegerProperty> getTovary() {
        return tovary;
    }
    
    public int getCelkovaCena() {
        return celkovaCena.getValue();
    }
    
    public IntegerProperty celkovaCenaProperty() {
        return celkovaCena;
    }
}
