package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Tovar;

public class MysqlTovarDaoTest {
    
    public MysqlTovarDaoTest() {
    }

    @Test
    public void testDajTovar() {
       MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
       List<Tovar> tovary = dao.dajTovar();
       
       Assert.assertTrue(tovary.size()>0);
    }

    @Test
    public void testPridajTovar() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        int pocetPred = dao.dajTovar().size();
        
        Tovar t = new Tovar();
        t.setKategoria(0L);
        t.setIdPodkategoria(0L);
        t.setNazov("nazov1");
        dao.pridajTovar(t);
        
         int pocetPo = dao.dajTovar().size();
         
         Assert.assertEquals(pocetPo, pocetPred+1);
    }

    @Test
    public void testOdstranTovar() {  
    }
    
}