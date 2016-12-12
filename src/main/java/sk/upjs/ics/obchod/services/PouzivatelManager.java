package sk.upjs.ics.obchod.services;

import javafx.beans.property.BooleanProperty;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface PouzivatelManager {
    
    public boolean prihlasPouzivatela(String meno, String heslo);
    
    public void registrujPouzivatela(String prihlasovacieMeno, String heslo, String email);
    
    Pouzivatel getAktivnyPouzivatel();
    
    BooleanProperty isPrihlaseny();
    
    void odhlasPouzivatela();
    
    boolean jeVolneMeno(String meno);
    
    void ulozPouzivatela(Pouzivatel pouzivatel);
    
    void zmenHeslo(Pouzivatel pouzivatel, String heslo);
    
    void setAktivnyPouzivatel(Pouzivatel aktivnyPouzivatel);
    
    void setPrihlaseny(BooleanProperty prihlaseny);
}
