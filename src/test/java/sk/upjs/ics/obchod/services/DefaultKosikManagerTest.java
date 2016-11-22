
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
        
        Tovar tovar = tovarDao.najdiPodlaId(0L);
        Kosik kosik = kosikDao.dajKosikPodlaId(0L);        
        
        boolean podariloSa = instance.pridajTovarDoKosika(tovar, kosik);
        
        Assert.assertTrue(podariloSa);
        
        
    }

    /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     */
    /*
    @Test
    public void testOdoberTovarZKosika() {
        System.out.println("odoberTovarZKosika");
        Tovar tovar = null;
        DefaultKosikManager instance = new DefaultKosikManager();
        boolean expResult = false;
        boolean result = instance.odoberTovarZKosika(tovar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
