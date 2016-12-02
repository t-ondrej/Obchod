package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public interface KosikDao {
    
    List<Kosik> dajKosiky();
    
    Kosik dajKosikPodlaId(Long idKosika);
    
    Long pridajKosikVratId();
    
    void odstranKosik(Long idKosik);
    
    /**
     * Da tovar do kosika a nastavi mu pocet 1
     * @param idTovaru
     * @param idKosika
     */
    void dajTovarDoKosika(Long idTovaru ,Long idKosika);
    
    int pocetJednehoTovaruVKosiku(Long idTovaru ,Long idKosika);
    
    /**
     * Nastavi pre dvojicu tovar kosik pocet bez ohladu na to aky pocet tam bol predtym
     * @param idTovaru
     * @param idKosika
     * @param pocet_kusov  pocet, ktory sa nastavi
     */
    void nastavTovaruVKosikuPocetKusov(Long idTovaru ,Long idKosika, int pocet_kusov);
    
    /**
     * Odoberie tovar z kosika bez ohladu na jeho pocet kusov
     * @param idTovaru
     * @param idKosika
     */
    void odoberTovarZKosika(Long idTovaru ,Long idKosika);
    
    List<Tovar> dajTovaryKosika(Long idKosika);
            
}
