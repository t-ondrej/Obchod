package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;

public interface FakturaDao {
    
    public List<Faktura> dajFaktury();
    
    public void pridajFakturu(Faktura faktura);
    
    public void odstranFakturu(Faktura faktura);
}
