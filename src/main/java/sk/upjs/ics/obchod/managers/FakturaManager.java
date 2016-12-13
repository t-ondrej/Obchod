package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;

public interface FakturaManager {
    
    /**
     * Vytvori fakturu a priradi k nej prislusne tovary aj s ich poctami, vyprazni kosik
     * @param pouzivatel
     * @return id vytvorenej faktury
     */
    Long vytvorFakturu(Pouzivatel pouzivatel);
    
    List<Faktura> dajFaktury();
    
    List<Faktura> dajFakturyZaObdobie(String obdobie);
    
    List<Tovar> dajTovarFaktury(Faktura faktura);
}
