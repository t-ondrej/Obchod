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

    private MysqlKosikDao dao;
    
    public MysqlKosikDaoTest(){
        dao = DaoFactory.INSTANCE.getMysqlKosikDao();
    }    
    
    @Test
    public void testDajKosiky() {        
        List<Kosik> kosiky = dao.dajKosiky();        
        Assert.assertTrue(kosiky.size()>0);
    }

    @Test
    public void testDajKosikPodlaId() {       
        Kosik kosik = dao.dajKosikPodlaId(0L);        
        Assert.assertTrue(kosik.getId() == 0L);
    }

    @Test
    public void testPridajKosikVratId() {       
        int pocetPred = dao.dajKosiky().size();        
        Long idKosik1 = dao.pridajKosikVratId();        
        int pocetPo = dao.dajKosiky().size();
        
        Assert.assertEquals(pocetPred + 1,pocetPo);
        
        Long idKosik2 = dao.pridajKosikVratId();        
        Assert.assertTrue(idKosik1 + 1 == idKosik2);
        
        dao.odstranKosik(idKosik1);
        dao.odstranKosik(idKosik2);
        
    }

    @Test
    public void testOdstranKosik() {
        Long idKosik = dao.pridajKosikVratId();
        
        int pocetPred = dao.dajKosiky().size();
        dao.odstranKosik(idKosik);
        int pocetPo = dao.dajKosiky().size();
        
        Assert.assertEquals(pocetPred - 1,pocetPo);
    }   

    /**
     * Test of dajTovaryKosika method, of class MysqlKosikDao.
     */
    @Test
    public void testDajTovaryKosika() {
        System.out.println("dajTovaryKosika");
        Long idKosika = 0L;
              
        List<Tovar> tovary = dao.dajTovaryKosika(idKosika);        
        Assert.assertTrue(tovary.size()>0);
    }

    /**
     * Test of dajTovarDoKosika method, of class MysqlKosikDao.
     */
    @Test
    public void testDajTovarDoKosika() {
        System.out.println("dajTovarDoKosika");
        Long idTovaru = 1L;
        Long idKosika = 0L;
        
        dao.dajTovarDoKosika(idTovaru, idKosika);
        
        List<Tovar> tovary = dao.dajTovaryKosika(idKosika);
        boolean jeTam = false;
        for(Tovar t: tovary){
            if (t.getId()==idTovaru){
                jeTam=true;
            }
        }
        Assert.assertTrue(jeTam);  
        dao.odoberTovarZKosika(idTovaru, idKosika);
    }

    /**
     * Test of odoberTovarZKosika method, of class MysqlKosikDao.
     */
    @Test
    public void testOdoberTovarZKosika() {
        System.out.println("odoberTovarZKosika");
        Long idTovaru = 1L;
        Long idKosika = 0L;
        
        dao.dajTovarDoKosika(idTovaru, idKosika);
        
        int pocPred = dao.dajTovaryKosika(idKosika).size();
        dao.odoberTovarZKosika(idTovaru, idKosika);
        int pocPo = dao.dajTovaryKosika(idKosika).size();
        
        Assert.assertEquals(pocPred-1, pocPo);
        
    }

    /**
     * Test of pocetJednehoTovaruVKosiku method, of class MysqlKosikDao.
     */
    @Test
    public void testPocetJednehoTovaruVKosiku() {
        System.out.println("pocetJednehoTovaruVKosiku");
        Long idTovaru = 0L;
        Long idKosika = 0L;       
        
        int result = dao.pocetJednehoTovaruVKosiku(idTovaru, idKosika);
        Assert.assertEquals(3, result);
        
    }

    /**
     * Test of nastavTovaruVKosikuPocetKusov method, of class MysqlKosikDao.
     */
    @Test
    public void testNastavTovaruVKosikuPocetKusov() {
        System.out.println("nastavTovaruVKosikuPocetKusov");
        Long idTovaru = 0L;
        Long idKosika = 0L;
        int pocet_kusov = 0;
        
        int pocetPred = dao.pocetJednehoTovaruVKosiku(idTovaru, idKosika);
        
        dao.nastavTovaruVKosikuPocetKusov(idTovaru, idKosika, pocet_kusov);        
        int pocet = dao.pocetJednehoTovaruVKosiku(idTovaru, idKosika);
        
        Assert.assertEquals(pocet_kusov, pocet);
        dao.nastavTovaruVKosikuPocetKusov(idTovaru, idKosika, pocetPred);  
        
    }
    
}
