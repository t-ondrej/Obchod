package sk.upjs.ics.obchod.dao;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class PamatovyKosikDaoTest {

    private PamatovyKosikDao dao;
    private Kosik kosik;
    private Tovar tovar1;
    private Tovar tovar2;
    private Tovar tovar3;

    public PamatovyKosikDaoTest() {
        dao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
        kosik = new Kosik();
    }

    private void naplnTestovacieUdaje() {
        tovar1 = new Tovar();
        tovar1.setId(1L);
        tovar1.setNazov("cokolada");
        
        Kategoria kategoria1 = new Kategoria();
        kategoria1.setId(1L);
        kategoria1.setNazov("Nezaradene1");      
        tovar1.setKategoria(kategoria1);
        
        Znacka znacka1 = new Znacka();
        znacka1.setId(1L);
        znacka1.setNazov("Nezaradene1");
        tovar1.setZnacka(znacka1);
        
        tovar1.setObrazokUrl("c:/files/cokolada");
        tovar1.setPocetKusov(2);
        tovar1.setCena(1);
        tovar1.setPopis("creative");
        
        tovar2 = new Tovar();
        tovar2.setId(2L);
        tovar2.setNazov("topanky");
        
        Kategoria kategoria0 = new Kategoria();
        kategoria0.setId(0L);
        kategoria0.setNazov("Nezaradene0");      
        tovar2.setKategoria(kategoria0);
        
        Znacka znacka0 = new Znacka();
        znacka0.setId(0L);
        znacka0.setNazov("Nezaradene0");
        tovar2.setZnacka(znacka0);
        
        tovar2.setObrazokUrl("c:/files/topanky");
        tovar2.setPocetKusov(3);
        tovar2.setCena(20);
        tovar2.setPopis("popis");
        
        tovar3 = new Tovar();
        tovar3.setId(3L);
        tovar3.setNazov("vec");
        
        Kategoria kategoria2 = new Kategoria();
        kategoria2.setId(2L);
        kategoria2.setNazov("Nezaradene2");      
        tovar3.setKategoria(kategoria2);
        
        Znacka znacka3 = new Znacka();
        znacka3.setId(1L);
        znacka3.setNazov("Nezaradene3");
        tovar3.setZnacka(znacka3);
        
        tovar3.setObrazokUrl("c:/files/vec");
        tovar3.setPocetKusov(5);
        tovar3.setCena(10);
        tovar3.setPopis("popis");

        kosik.getTovar().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getTovar().put(tovar2, new SimpleIntegerProperty(4));
        kosik.setCelkovaCena(81);
    }

    
    @After
    public void vymazTestovacieUdaje() {
        kosik = null;
    }

    /**
     * Test of dajTovarDoKosika method, of class PamatoviKosikDao.
     * Tovar nebol v kosiku
     */
    @Test
    public void testDajTovarDoKosikaNovyTovar() {
        System.out.println("dajTovarDoKosikaNovyTovar");
        naplnTestovacieUdaje();
        
        dao.dajTovarDoKosika(tovar3, kosik);
        
        Assert.assertTrue(kosik.getTovar().containsKey(tovar3));
        Assert.assertEquals(1, kosik.getTovar().get(tovar3).intValue());
        Assert.assertEquals(91, kosik.getCelkovaCena());        
    }
    
    /**
     * Test of dajTovarDoKosika method, of class PamatoviKosikDao.
     * Uz je v kosiku
     */
    @Test
    public void testDajTovarDoKosikaUzJeV() {
        System.out.println("dajTovarDoKosikaUzJeV");
        naplnTestovacieUdaje();
        
        dao.dajTovarDoKosika(tovar2, kosik);
        
        Assert.assertTrue(kosik.getTovar().containsKey(tovar2));
        Assert.assertEquals(5, kosik.getTovar().get(tovar2).intValue());
        Assert.assertEquals(101, kosik.getCelkovaCena());        
    }

    /**
     * Test of pocetJednehoTovaruVKosiku method, of class PamatoviKosikDao.
     */
    @Test
    public void testPocetJednehoTovaruVKosiku() {
        System.out.println("pocetJednehoTovaruVKosiku");
        naplnTestovacieUdaje();

        int pocet = dao.pocetJednehoTovaruVKosiku(tovar1, kosik);
        Assert.assertEquals(1, pocet);
    }

    /**
     * Test of nastavTovaruVKosikuPocetKusov method, of class PamatoviKosikDao.
     */
    @Test
    public void testNastavTovaruVKosikuPocetKusov() {
        System.out.println("nastavTovaruVKosikuPocetKusov");
        naplnTestovacieUdaje();

        dao.nastavTovaruVKosikuPocetKusov(tovar2, kosik, 10);
        Assert.assertEquals(10, kosik.getTovar().get(tovar2).intValue());
    }

    /**
     * Test of odoberTovarZKosika method, of class PamatoviKosikDao.
     * Je tam jeden kus
     */
    @Test
    public void testOdoberTovarZKosikaJedenKusJeTam() {
        System.out.println("odoberTovarZKosikaJedenKusJeTam");
        naplnTestovacieUdaje();

        dao.odoberTovarZKosika(tovar1, kosik);
        IntegerProperty pocet = kosik.getTovar().get(tovar1);

        Assert.assertEquals(null, pocet);        
        Assert.assertEquals(80, kosik.getCelkovaCena());
        Assert.assertEquals(1, kosik.getTovar().size());
    }
    
    /**
     * Test of odoberTovarZKosika method, of class PamatoviKosikDao.
     * Je tam viac kusov
     */
    @Test
    public void testOdoberTovarZKosikaViacKusovJeTam() {
        System.out.println("odoberTovarZKosikaViacKusovJeTam");
        naplnTestovacieUdaje();

        dao.odoberTovarZKosika(tovar2, kosik);
        IntegerProperty pocet = kosik.getTovar().get(tovar2);

        Assert.assertEquals(new SimpleIntegerProperty(3).getValue(), pocet.getValue());        
        Assert.assertEquals(61, kosik.getCelkovaCena());
        Assert.assertEquals(2, kosik.getTovar().size());
    }

    /**
     * Test of dajTovarKosika method, of class PamatoviKosikDao.
     */
    @Test
    public void testDajTovarKosika() {
        System.out.println("dajTovarKosika");
        naplnTestovacieUdaje();

        List<Tovar> tovar = dao.dajTovarKosika(kosik);
        Assert.assertEquals(2, tovar.size());
        Assert.assertTrue(tovar.contains(tovar1));
        Assert.assertTrue(tovar.contains(tovar2));
    }
}
