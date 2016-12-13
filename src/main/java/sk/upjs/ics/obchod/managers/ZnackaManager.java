package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Znacka;

public interface ZnackaManager {

    List<Znacka> dajZnacky();

    void odstranZnacku(Znacka znacka);
    
    void upravZnacku(Znacka znacka, String nazov);
    
    Long pridajZnacku(Znacka znacka);
    
    boolean existujeZnackaSNazvom(String nazov);
    
    boolean existujeTovarSoZnackou(Znacka znacka);
}
