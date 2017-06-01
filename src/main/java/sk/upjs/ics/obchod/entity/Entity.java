package sk.upjs.ics.obchod.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public abstract class Entity {
   
    private LongProperty id = new SimpleLongProperty();

    public Entity() {
    }

    public Entity(Long id) {
        this.id.setValue(id);
    }
    
    public Long getId()
    {
        return id.getValue();
    }

    public void setId(Long id)
    {
        this.id.setValue(id);
    }
    
    public LongProperty idProperty() {
        return id;
    }
}
