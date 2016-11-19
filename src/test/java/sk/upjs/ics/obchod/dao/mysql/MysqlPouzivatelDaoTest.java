
package sk.upjs.ics.obchod.dao.mysql;

import sk.upjs.ics.obchod.dao.mysql.MysqlPouzivatelDao;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Pouzivatel;

/**
 *
 * @author User
 */
public class MysqlPouzivatelDaoTest {
    
   
    /**
     * Test of dajPouzivatelov method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelov() {
        System.out.println("dajPouzivatelov");
        MysqlPouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
        List<Pouzivatel> pouzivatelia = dao.dajPouzivatelov();
        
         Assert.assertTrue(pouzivatelia.size()>0);
        
    }

    /**
     * Test of pridajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testPridajPouzivatela() {
        System.out.println("pridajPouzivatela");
        
        
    }

    /**
     * Test of odstranPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testOdstranPouzivatela() {
        System.out.println("odstranPouzivatela");
       
    }
    
}
