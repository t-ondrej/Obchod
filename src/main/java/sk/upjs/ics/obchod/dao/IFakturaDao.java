package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Tovar;

public interface IFakturaDao {
    
    List<Faktura> dajFaktury();
    
    List<Faktura> dajFakturyZaPoslednyDen();
    
    List<Faktura> dajFakturyZaPoslednyTyzden();
    
    List<Faktura> dajFakturyZaPoslednyMesiac();
    
    List<Faktura> dajFakturyZaPoslednyRok();
    
    Long pridajFakturu(Faktura faktura);
    
    void odstranFakturu(Faktura faktura);
    
    void pridajTovarFakture(Tovar tovar, Faktura faktura, int pocetTovaru);
    
    List<Tovar> dajTovarFaktury(Faktura faktura);
    
}
