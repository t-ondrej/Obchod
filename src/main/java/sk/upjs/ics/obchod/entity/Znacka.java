package sk.upjs.ics.obchod.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Znacka {
    
    private LongProperty id = new SimpleLongProperty(); 
    
    private StringProperty nazov = new SimpleStringProperty();
   
    public Long getId() {
        return id.getValue();
    }

    public void setId(Long id) {
        this.id.setValue(id);
    }
    
    public LongProperty idProperty() {
        return id;
    }

    public String getNazov() {
        return nazov.getValue();
    }

    public void setNazov(String nazov) {
        this.nazov.setValue(nazov);
    }
    
    public StringProperty nazovProperty() {
        return nazov;
    }

}
