package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;

public interface FakturaDao {
    
    List<Faktura> dajFaktury();
    
    void pridajFakturu(Faktura faktura);
    
    void odstranFakturu(Faktura faktura);
}
