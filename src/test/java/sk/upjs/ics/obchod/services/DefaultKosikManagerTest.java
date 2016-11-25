
package sk.upjs.ics.obchod.services;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;


public class DefaultKosikManagerTest {

    
   
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     */
    @Test
    public void testPridajTovarDoKosika() {
        System.out.println("pridajTovarDoKosika");
        DefaultKosikManager instance = new DefaultKosikManager();
        KosikDao kosikDao = DaoFactory.INSTANCE.getMysqlKosikDao();
        TovarDao tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        // tovar2 sa neda pridat, lebo ma nolovy pocet kusov
        Tovar tovar2 = tovarDao.najdiPodlaId(2L);
        Kosik kosik2 = kosikDao.dajKosikPodlaId(0L);        
        
        boolean pridaloSa = instance.pridajTovarDoKosika(tovar2, kosik2);
        System.out.println("pridajTovarDoKosika, ktory nema dost kusov");
        Assert.assertTrue(!pridaloSa);
        
        // tovar1 este v kosiku nie je
        Tovar tovar1 = tovarDao.najdiPodlaId(1L);
        Kosik kosik1 = kosikDao.dajKosikPodlaId(0L);
        
        int pocTovarovVKosikuPred = kosikDao.dajTovaryKosika(kosik1.getId()).size();        
        int pocTovaruPred = tovarDao.dajPocetTovaru(tovar1.getId());
        instance.pridajTovarDoKosika(tovar1, kosik1);
        int pocTovarovVKosikuPo = kosikDao.dajTovaryKosika(kosik1.getId()).size();
        int pocZJednehoTovaruVKosikuPo = kosikDao.pocetJednehoTovaruVKosiku(tovar1.getId(), kosik1.getId());
        int pocTovaruPo = tovarDao.dajPocetTovaru(tovar1.getId());
        
        System.out.println("pridajTovarDoKosika, ktory zatial nie je v kosiku");
        Assert.assertEquals(pocTovarovVKosikuPred+1, pocTovarovVKosikuPo); 
        Assert.assertEquals(1, pocZJednehoTovaruVKosikuPo); 
        Assert.assertEquals(pocTovaruPred-1, pocTovaruPo);   
        
        // tovar 0 uz je v kosiku
        Tovar tovar0 = tovarDao.najdiPodlaId(0L);
        Kosik kosik0 = kosikDao.dajKosikPodlaId(0L);
        
        int pocTovarovVKosikuPred0 = kosikDao.dajTovaryKosika(kosik0.getId()).size();
        int pocZJednehoTovaruVKosikuPred0 = kosikDao.pocetJednehoTovaruVKosiku(tovar0.getId(), kosik0.getId());
        int pocTovaruPred0 = tovarDao.dajPocetTovaru(tovar0.getId());
        instance.pridajTovarDoKosika(tovar0, kosik0);
        int pocTovarovVKosikuPo0 = kosikDao.dajTovaryKosika(kosik0.getId()).size();
        int pocZJednehoTovaruVKosikuPo0 = kosikDao.pocetJednehoTovaruVKosiku(tovar0.getId(), kosik0.getId());
        int pocTovaruPo0 = tovarDao.dajPocetTovaru(tovar0.getId());
        
        System.out.println("pridajTovarDoKosika, ktory uz je v kosiku");
        Assert.assertEquals(pocTovarovVKosikuPred0, pocTovarovVKosikuPo0); 
        Assert.assertEquals(pocZJednehoTovaruVKosikuPred0+1, pocZJednehoTovaruVKosikuPo0);
        Assert.assertEquals(pocTovaruPred0-1, pocTovaruPo0); 
        
        instance.odoberTovarZKosika(tovar1, kosik1);
        instance.odoberTovarZKosika(tovar0, kosik0);
    }

    /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     */
    @Test
    public void testOdoberTovarZKosika() {
        System.out.println("odoberTovarZKosika");
        DefaultKosikManager instance = new DefaultKosikManager();
        KosikDao kosikDao = DaoFactory.INSTANCE.getMysqlKosikDao();
        TovarDao tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        Tovar tovar1 = tovarDao.najdiPodlaId(1L);
        Kosik kosik1 = kosikDao.dajKosikPodlaId(0L);
        instance.pridajTovarDoKosika(tovar1, kosik1);
        
        int pocTovarovVKosikuPred = kosikDao.dajTovaryKosika(kosik1.getId()).size(); 
        int pocTovaruPred = tovarDao.dajPocetTovaru(tovar1.getId());
        instance.odoberTovarZKosika(tovar1, kosik1);
        int pocTovarovVKosikuPo = kosikDao.dajTovaryKosika(kosik1.getId()).size();
        int pocTovaruPo = tovarDao.dajPocetTovaru(tovar1.getId());
        
        Assert.assertEquals(pocTovarovVKosikuPred-1, pocTovarovVKosikuPo);         
        Assert.assertEquals(pocTovaruPred+1, pocTovaruPo);
        
        Tovar tovar0 = tovarDao.najdiPodlaId(0L);
        Kosik kosik0 = kosikDao.dajKosikPodlaId(0L);
        instance.pridajTovarDoKosika(tovar0, kosik0);
        
        int pocTovarovVKosikuPred0 = kosikDao.dajTovaryKosika(kosik0.getId()).size();
        int pocZJednehoTovaruVKosikuPred0 = kosikDao.pocetJednehoTovaruVKosiku(tovar0.getId(), kosik0.getId());
        int pocTovaruPred0 = tovarDao.dajPocetTovaru(tovar0.getId());
        instance.odoberTovarZKosika(tovar0, kosik0);
        int pocTovarovVKosikuPo0 = kosikDao.dajTovaryKosika(kosik0.getId()).size();
        int pocZJednehoTovaruVKosikuPo0 = kosikDao.pocetJednehoTovaruVKosiku(tovar0.getId(), kosik0.getId());
        int pocTovaruPo0 = tovarDao.dajPocetTovaru(tovar0.getId());
        
        Assert.assertEquals(pocTovarovVKosikuPred0, pocTovarovVKosikuPo0); 
        Assert.assertEquals(pocZJednehoTovaruVKosikuPred0-1, pocZJednehoTovaruVKosikuPo0);
        Assert.assertEquals(pocTovaruPred0+1, pocTovaruPo0); 
    }

    
    
}
