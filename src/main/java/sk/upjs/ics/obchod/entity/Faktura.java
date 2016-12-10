package sk.upjs.ics.obchod.entity;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Faktura {
    
    private LongProperty id = new SimpleLongProperty();
    
    private LongProperty idPouzivatel = new SimpleLongProperty();
     
    private IntegerProperty suma = new SimpleIntegerProperty();
    
    private ObjectProperty<LocalDate> datumNakupu = new SimpleObjectProperty();
    
    public Long getId() {
        return id.getValue();
    }

    public void setId(Long id) {
        this.id.setValue(id);
    }
    
    public LongProperty idProperty() {
        return id;
    }
    
    public Long getIdPouzivatel() {
        return idPouzivatel.getValue();
    }

    public void setIdPouzivatel(Long idPouzivatel) {
        this.idPouzivatel.setValue(idPouzivatel);
    }
    
    public LongProperty idPouzivatelProperty() {
        return idPouzivatel;
    }

    public int getSuma() {
        return suma.getValue();
    }

    public void setSuma(int suma) {
        this.suma.setValue(suma);
    }

    public IntegerProperty sumaProperty() {
        return suma;
    }
    
    public LocalDate getDatumNakupu() {
        return datumNakupu.getValue();
    }

    public void setDatumNakupu(LocalDate datumNakupu) {
        this.datumNakupu.setValue(datumNakupu);
    }    
    
    public ObjectProperty datumNakupuProperty() {
        return datumNakupu;
    }
}
