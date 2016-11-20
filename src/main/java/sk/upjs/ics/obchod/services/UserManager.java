package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface UserManager {
    
    public void prihlasPouzivatela(String meno, String heslo);
    
    public void registrujPouzivatela(Pouzivatel pouzivatel);
}
