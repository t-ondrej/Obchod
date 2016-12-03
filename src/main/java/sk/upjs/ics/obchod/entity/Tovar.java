package sk.upjs.ics.obchod.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tovar {
    
    private LongProperty id = new SimpleLongProperty();
    
    private StringProperty nazov = new SimpleStringProperty();
    
    private LongProperty idZnacka = new SimpleLongProperty();
    
    private LongProperty idKategoria = new SimpleLongProperty();;
    
    private IntegerProperty cena = new SimpleIntegerProperty();
    
    private StringProperty popis = new SimpleStringProperty();;
    
    private StringProperty obrazokUrl = new SimpleStringProperty();;
    
    private IntegerProperty pocetKusov = new SimpleIntegerProperty();;
    
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
    
    public Long getIdZnacka() {
        return idZnacka.getValue();
    }

    public void setIdZnacka(Long idZnacka) {
        this.idZnacka.setValue(idZnacka);
    }
    
    public LongProperty idZnackaProperty() {
        return idZnacka;
    }

    public Long getIdKategoria() {
        return idKategoria.getValue();
    }

    public void setIdKategoria(Long idKategoria) {
        this.idKategoria.setValue(idKategoria);
    }
    
    public LongProperty idKategoriaProperty() {
        return idKategoria;
    }

    public int getCena() {
        return cena.getValue();
    }

    public void setCena(int cena) {
        this.cena.setValue(cena);
    }
    
    public IntegerProperty cenaProperty() {
        return cena;
    }

    public String getPopis() {
        return popis.getValue();
    }

    public void setPopis(String popis) {
        this.popis.setValue(popis);
    }
    
    public StringProperty popisProperty() {
        return popis;
    }

    public String getObrazokUrl() {
        return obrazokUrl.getValue();
    }

    public void setObrazokUrl(String obrazokUrl) {
        this.obrazokUrl.setValue(obrazokUrl);
    }
    
    public StringProperty obrazokUrl() {
        return obrazokUrl;
    }
    
    public int getPocetKusov() {
        return pocetKusov.getValue();
    }

    public void setPocetKusov(int pocetKusov) {
        this.pocetKusov.setValue(pocetKusov);
    }
    
    public IntegerProperty pocetKusovProperty() {
        return pocetKusov;
    }
    
}
