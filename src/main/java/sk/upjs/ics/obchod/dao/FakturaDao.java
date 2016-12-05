package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Tovar;

public interface FakturaDao {
    
    List<Faktura> dajFaktury();
    
    Long pridajFakturu(Faktura faktura);
    
    void odstranFakturu(Faktura faktura);
    
    void pridajTovarFakture(Tovar tovar ,Faktura faktura, int pocetTovaru);
}
