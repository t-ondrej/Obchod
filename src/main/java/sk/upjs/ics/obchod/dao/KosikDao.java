package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikDao {  
    
    /**
     * Da tovar do kosika a nastavi mu pocet 1
     * @param Tovar
     * @param Kosik
     */
    void dajTovarDoKosika(Tovar tovar ,Kosik kosik);
    
    int pocetJednehoTovaruVKosiku(Tovar tovar ,Kosik kosik);
    
    /**
     * Nastavi pre dvojicu tovar kosik pocet bez ohladu na to aky pocet tam bol predtym
     * @param Tovar
     * @param Kosik
     * @param pocet_kusov  pocet, ktory sa nastavi
     */
    void nastavTovaruVKosikuPocetKusov(Tovar tovar ,Kosik kosik, int pocet_kusov);
    
    /**
     * Odoberie tovar z kosika bez ohladu na jeho pocet kusov
     * @param idTovaru
     * @param idKosika
     */
    void odoberTovarZKosika(Tovar tovar ,Kosik kosik);
    
    List<Tovar> dajTovaryKosika(Kosik kosik);
            
}
