package sk.upjs.ics.obchod.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tovar {

    private LongProperty id = new SimpleLongProperty();

    private StringProperty nazov = new SimpleStringProperty();

    private ObjectProperty<Znacka> znacka = new SimpleObjectProperty<>();

    private ObjectProperty<Kategoria> kategoria = new SimpleObjectProperty<>();

    private IntegerProperty cena = new SimpleIntegerProperty();

    private StringProperty popis = new SimpleStringProperty();
    ;
    
    private StringProperty obrazokUrl = new SimpleStringProperty();
    ;
    
    private IntegerProperty pocetKusov = new SimpleIntegerProperty();

    ;
    
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

    public Znacka getZnacka() {
        return znacka.getValue();
    }

    public void setZnacka(Znacka znacka) {
        this.znacka.setValue(znacka);
    }

    public ObjectProperty<Znacka> znackaProperty() {
        return znacka;
    }

    public Kategoria getKategoria() {
        return kategoria.getValue();
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria.setValue(kategoria);
    }

    public ObjectProperty<Kategoria> kategoriaProperty() {
        return kategoria;
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

    @Override
    public int hashCode() {
        int hash = this.getCena();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Tovar other = (Tovar) obj;

        return this.getNazov().equals(other.getNazov());

    }
}
