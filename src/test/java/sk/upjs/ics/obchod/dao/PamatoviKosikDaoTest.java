package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatoviKosikDaoTest {

    private PamatoviKosikDao dao;
    private Kosik kosik;
    private Tovar tovar1;
    private Tovar tovar2;

    public PamatoviKosikDaoTest() {
        dao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
        kosik = new Kosik();
    }

    private void naplnTestovacieUdaje() {
        tovar1 = new Tovar();
        tovar1.setId(0L);
        tovar2 = new Tovar();
        tovar2.setId(1L);

        dao.dajTovarDoKosika(tovar1, kosik);
        dao.nastavTovaruVKosikuPocetKusov(tovar1, kosik, 2);
        
        dao.dajTovarDoKosika(tovar2, kosik);
        dao.nastavTovaruVKosikuPocetKusov(tovar2, kosik, 4);
    }

    
    @After
    public void vymazTestovacieUdaje() {
        kosik = null;
    }

    /**
     * Test of dajTovarDoKosika method, of class PamatoviKosikDao.
     */
    @Test
    public void testDajTovarDoKosika() {
        System.out.println("dajTovarDoKosika");
        naplnTestovacieUdaje();

        Tovar tovar = new Tovar();
        tovar.setId(2L);
        dao.dajTovarDoKosika(tovar, kosik);

        Assert.assertTrue(kosik.getTovary().containsKey(tovar));
        Assert.assertEquals(1, kosik.getTovary().get(tovar).intValue());
    }

    /**
     * Test of pocetJednehoTovaruVKosiku method, of class PamatoviKosikDao.
     */
    @Test
    public void testPocetJednehoTovaruVKosiku() {
        System.out.println("pocetJednehoTovaruVKosiku");
        naplnTestovacieUdaje();

        int pocet = dao.pocetJednehoTovaruVKosiku(tovar1, kosik);
        Assert.assertEquals(2, pocet);
    }

    /**
     * Test of nastavTovaruVKosikuPocetKusov method, of class PamatoviKosikDao.
     */
    @Test
    public void testNastavTovaruVKosikuPocetKusov() {
        System.out.println("nastavTovaruVKosikuPocetKusov");
        naplnTestovacieUdaje();

        dao.nastavTovaruVKosikuPocetKusov(tovar2, kosik, 10);
        Assert.assertEquals(10, kosik.getTovary().get(tovar2).intValue());
    }

    /**
     * Test of odoberTovarZKosika method, of class PamatoviKosikDao.
     */
    @Test
    public void testOdoberTovarZKosika() {
        System.out.println("odoberTovarZKosika");
        naplnTestovacieUdaje();

        dao.odoberTovarZKosika(tovar1, kosik);
        int pocetPoOdstraneni = kosik.getTovary().get(tovar1).getValue().intValue();

        Assert.assertEquals(1, pocetPoOdstraneni);
        //Assert.assertTrue(!(kosik.getTovary().containsKey(tovar1)));
    }

    /**
     * Test of dajTovaryKosika method, of class PamatoviKosikDao.
     */
    @Test
    public void testDajTovaryKosika() {
        System.out.println("dajTovaryKosika");
        naplnTestovacieUdaje();

        List<Tovar> tovary = dao.dajTovaryKosika(kosik);
        Assert.assertEquals(2, tovary.size());
        Assert.assertTrue(tovary.contains(tovar1));
        Assert.assertTrue(tovary.contains(tovar2));
    }
}
