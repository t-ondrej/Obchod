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
       
       Assert.assertEquals(1, tovary.size());
    }

    @Test
    public void testPridajTovar() {
    }

    @Test
    public void testOdstranTovar() {
    }
    
}
