package sk.upjs.ics.obchod.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Brand extends Entity{
        
    private StringProperty name = new SimpleStringProperty();  

    public Brand() {
        
    }
    
    public Brand(Long id, String name) {
        super(id);
        this.name.setValue(name);
    }
 
    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
    
    public StringProperty nameProperty() {
        return name;
    }

}
