
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Znacka;

/**
 *
 * @author User
 */
public class MysqlZnackaDaoTest {
    
    

    /**
     * Test of dajZnacky method, of class MysqlZnackaDao.
     */
    @Test
    public void testDajZnacky() {
        System.out.println("dajZnacky");
         MysqlZnackaDao dao = DaoFactory.INSTANCE.getMysqlZnackaDao();
        List<Znacka> znacky= dao.dajZnacky();
        
        Assert.assertTrue(znacky.size()>0);
    }

    /**
     * Test of najdiPodlaId method, of class MysqlZnackaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        
        MysqlZnackaDao dao = DaoFactory.INSTANCE.getMysqlZnackaDao();
        Znacka z = dao.najdiPodlaId(0L);
        
        Assert.assertEquals(z.getNazov(), "Neznama");
        
    }

    /**
     * Test of uloz method, of class MysqlZnackaDao.
     */
    @Test
    public void testUloz() {
        System.out.println("uloz");
        
        MysqlZnackaDao dao = DaoFactory.INSTANCE.getMysqlZnackaDao();
         int pocetPred = dao.dajZnacky().size();
         
         Znacka z = new Znacka();
         z.setNazov("skusobna");
         dao.uloz(z);
         
         int pocetPo = dao.dajZnacky().size();
         Assert.assertEquals(pocetPo, pocetPred+1);
    }
    
}
