package sk.upjs.ics.obchod.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TovarModel {

    private StringProperty nazov;

    private IntegerProperty cena;

    private IntegerProperty pocetKusovVKosiku;

    public TovarModel(String nazov, int cena, int pocetKusovVKosiku) {
        this.nazov = new SimpleStringProperty(nazov) ;
        this.cena = new SimpleIntegerProperty(cena);
        this.pocetKusovVKosiku = new SimpleIntegerProperty(pocetKusovVKosiku);
    }

    public StringProperty getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov.setValue(nazov);
    }

    public IntegerProperty getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena.setValue(cena);
    }

    public IntegerProperty getPocetKusovVKosiku() {
        return pocetKusovVKosiku;
    }

    public void setPocetKusovVKosiku(int pocetTovaru) {
        this.pocetKusovVKosiku.set(pocetTovaru);
    }

}
