package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface PouzivatelManager {
    
    public void prihlasPouzivatela(String meno, String heslo);
    
    public void registrujPouzivatela(Pouzivatel pouzivatel);
}
