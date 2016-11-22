package sk.upjs.ics.obchod.dao.mysql;

import sk.upjs.ics.obchod.dao.mysql.MysqlTovarDao;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.dao.DaoFactory;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlTovarDaoTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

   
    @Test
    public void testDajTovar() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        List<Tovar> tovary = dao.dajTovar();

        Assert.assertTrue(tovary.size() > 0);
    }

    @Test
    public void testDajTovarPodlaKategorie() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        Kategoria kategoria = new Kategoria();
        kategoria.setId(1L);
        kategoria.setNazov("Kategoria1Test");
        
        List<Tovar> tovary = dao.dajTovarPodlaKategorie(kategoria);
        Tovar tovar = tovary.get(0);
        
        Assert.assertEquals(tovar.getIdKategoria(), kategoria.getId());
    }   
    
        @Test
    public void testDajTovarPodlaNazvu() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        List<Tovar> tovary = dao.dajTovarPodlaNazvu("Tovar1Test");
        Tovar tovar = tovary.get(0);
        
        Assert.assertEquals("Tovar1Test", tovar.getNazov());      
    }
        
        @Test
    public void testDajTovarPodlaZnacky() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        
        Znacka znacka = new Znacka();
        znacka.setId(1L);
        znacka.setNazov("Znacka1Test");
        
        List<Tovar> tovary = dao.dajTovarPodlaZnacky(znacka);
        Tovar tovar = tovary.get(0);
        
        Assert.assertTrue(1L == tovar.getIdZnacka());
    }
    
    @Test
    public void testPridajTovar() {
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();

        int pocetPred = dao.dajTovar().size();

        Tovar t = new Tovar();
        t.setIdKategoria(0L);
        t.setIdZnacka(0L);
        t.setNazov("nazov1");
        dao.pridajTovar(t);

        int pocetPo = dao.dajTovar().size();

        Assert.assertEquals(pocetPo, pocetPred + 1);
    }

    @Test
    public void testOdstranTovar() {
    }

    /**
     * Test of najdiPodlaId method, of class MysqlTovarDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        Tovar tovar = dao.najdiPodlaId(0L);
        Assert.assertNotNull(tovar);
    }

    /**
     * Test of zvisTovaruPocetKusov method, of class MysqlTovarDao.
     */
    @Test
    public void testNastavTovaruPocetKusov() {
        System.out.println("nastavTovaruPocetKusov");
        MysqlTovarDao dao = DaoFactory.INSTANCE.getMysqlTovarDao();
        Long idTovaru = 0L;   
        Tovar tovar = dao.najdiPodlaId(idTovaru);
        int pocetPred = tovar.getPocet_kusov();
        dao.nastavTovaruPocetKusov(tovar,pocetPred+1);
        int pocetPo = dao.najdiPodlaId(idTovaru).getPocet_kusov();
        
        Assert.assertEquals(pocetPo, pocetPred+1);
    }

    

}
