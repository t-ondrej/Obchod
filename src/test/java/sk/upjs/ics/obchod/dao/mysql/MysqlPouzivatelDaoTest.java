package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
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
    
    @Test
    public void testDajPouzivatelaPodlaId() {
        MysqlPouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
        Pouzivatel pouzivatel = dao.dajPouzivatela(0L);
        
        Assert.assertTrue(0L == pouzivatel.getId());
    }
    
    @Test
    public void testDajPouzivatelaPodlaMena() {
        MysqlPouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
        Pouzivatel pouzivatel;
        
        pouzivatel = dao.dajPouzivatela("neznameMeno");       
        Assert.assertTrue(null == pouzivatel);
        
        pouzivatel = dao.dajPouzivatela("testPouzivatel");
        Assert.assertTrue("testPouzivatel".equals(pouzivatel.getPrihlasovacieMeno()));
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
