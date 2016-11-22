package sk.upjs.ics.obchod.dao.mysql;

import sk.upjs.ics.obchod.dao.mysql.MysqlKosikDao;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class MysqlKosikDaoTest {    

    
    
    @Test
    public void testDajKosiky() {
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();
        List<Kosik> kosiky = dao.dajKosiky();
        
        Assert.assertTrue(kosiky.size()>0);
    }

    @Test
    public void testDajKosikPodlaId() {
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();
        Kosik kosik = dao.dajKosikPodlaId(0L);
        
        Assert.assertTrue(kosik.getId() == 0L);
    }

    @Test
    public void testPridajKosikVratId() {
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();
        int pocetPred = dao.dajKosiky().size();
        
        Long idKosik1 = dao.pridajKosikVratId(new Kosik());
        
        int pocetPo = dao.dajKosiky().size();
        
        Assert.assertEquals(pocetPred + 1,pocetPo);
        
        Long idKosik2 = dao.pridajKosikVratId(new Kosik());
        
        Assert.assertTrue(idKosik1 + 1 == idKosik2);
    }

    @Test
    public void testOdstranKosik() {
    }

    /**
     * Test of dajDoKosikaTovar method, of class MysqlKosikDao.
     */
    @Test
    public void testTovarDoKosika() {
        System.out.println("dajTovarDoKosika");
        Long idKosika = 0L;
        Long idTovaru = 0L;
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();
        dao.dajTovarDoKosika(idTovaru, idKosika);
        List<Tovar> tovary = dao.dajTovaryKosika(idKosika);
        boolean jeTam = false;
        for(Tovar t: tovary){
            if(t.getId()==idTovaru){
                jeTam = true;
            }
        }
        Assert.assertTrue(jeTam);
    }

    /**
     * Test of dajTovaryKosika method, of class MysqlKosikDao.
     */
    @Test
    public void testDajTovaryKosika() {
        System.out.println("dajTovaryKosika");
        Long idKosika = 0L;
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();        
        List<Tovar> tovary = dao.dajTovaryKosika(idKosika);
        Assert.assertTrue(tovary.size()>0);
    }
    
}
