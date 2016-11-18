
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Kategoria;

/**
 *
 * @author User
 */
public class MysqlKategoriaDaoTest {
    
    
    /**
     * Test of dajKategorie method, of class MysqlKategoriaDao.
     */
    @Test
    public void testDajKategorie() {
        System.out.println("dajKategorie");
        MysqlKategoriaDao dao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        List<Kategoria> kategorie= dao.dajKategorie();
        
        Assert.assertTrue(kategorie.size()>0);
        
    }

    /**
     * Test of najdiPodlaId method, of class MysqlKategoriaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        
        MysqlKategoriaDao dao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
        Kategoria k = dao.najdiPodlaId(0L);
        
        Assert.assertEquals(k.getNazov(), "Nezaradene");
        
    }

    /**
     * Test of uloz method, of class MysqlKategoriaDao.
     */
    @Test
    public void testUloz() {
        System.out.println("uloz");
       
        MysqlKategoriaDao dao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
         int pocetPred = dao.dajKategorie().size();
         
         Kategoria kategoria = new Kategoria();
         kategoria.setNazov("skusobna");
         dao.uloz(kategoria);
         
         int pocetPo = dao.dajKategorie().size();
         Assert.assertEquals(pocetPo, pocetPred+1);
        
    }
    
}
