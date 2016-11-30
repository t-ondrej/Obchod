package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.entity.Faktura;

public class MysqlFakturaDaoTest {
    
    @Test
    public void testDajFaktury() {
        MysqlFakturaDao dao = DaoFactory.INSTANCE.getMysqlFakturaDao();
        List<Faktura> faktury = dao.dajFaktury();
        
        Assert.assertTrue(faktury.size() > 0);
    }

    @Test
    public void testPridajFakturu() {
        MysqlFakturaDao dao = DaoFactory.INSTANCE.getMysqlFakturaDao();
        int pocetPred = dao.dajFaktury().size();
        
        Faktura faktura = new Faktura();
        
        faktura.setIdPouzivatel(0L);
        faktura.setSuma(50);
        faktura.setDatumNakupu(new Date(2016, 10, 10));
        
        dao.pridajFakturu(faktura);
        
        int pocetPo = dao.dajFaktury().size();
        
        Assert.assertEquals(pocetPred + 1, pocetPo);
    }

    @Test
    public void testOdstranFakturu() {
    }
    
}
