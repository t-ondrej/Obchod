package sk.upjs.ics.obchod.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category extends Entity {
       
    private StringProperty name = new SimpleStringProperty();

    public Category() {
    }

    public Category(Long id, String name) {
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
