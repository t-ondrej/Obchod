package sk.upjs.ics.obchod.dao.mysql;

import sk.upjs.ics.obchod.dao.mysql.MysqlKosikDao;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Kosik;

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
    public void testPridajKosik() {
        MysqlKosikDao dao = DaoFactory.INSTANCE.getMysqlKosikDao();
        int pocetPred = dao.dajKosiky().size();
        
        dao.pridajKosik(new Kosik());
        
        int pocetPo = dao.dajKosiky().size();
        
        Assert.assertEquals(pocetPred + 1,pocetPo);
    }

    @Test
    public void testOdstranKosik() {
    }
    
}
