package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface PouzivatelManager {
    
    public boolean prihlasPouzivatela(String meno, String heslo);
    
    public void registrujPouzivatela(Pouzivatel pouzivatel);
}
