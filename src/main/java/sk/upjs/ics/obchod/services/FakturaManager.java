package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.entity.Pouzivatel;

public interface FakturaManager {
    
    /**
     * Vytvori fakturu a priradi k nej prislusne tovary aj s ich poctami, vyprazni kosik
     * @param pouzivatel
     * @return id vytvorenej faktury
     */
    Long vytvorFakturu(Pouzivatel pouzivatel);
    
}
