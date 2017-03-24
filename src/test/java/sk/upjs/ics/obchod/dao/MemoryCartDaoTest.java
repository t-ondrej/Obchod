package sk.upjs.ics.obchod.dao;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public class MemoryCartDaoTest {

    private MemoryCartDao kosiKDao;
    private Cart kosik;
    private Product tovar1;
    private Product tovar2;
    private Product tovar3;

    public MemoryCartDaoTest() {
        kosiKDao = new MemoryCartDao();
        kosik = new Cart();
    }

    private void naplnTestovacieUdaje() {
        tovar1 = new Product();
        tovar1.setId(1L);
        tovar1.setName("cokolada");
        
        Category kategoria1 = new Category();
        kategoria1.setId(1L);
        kategoria1.setName("Nezaradene1");      
        tovar1.setCategory(kategoria1);
        
        Brand znacka1 = new Brand();
        znacka1.setId(1L);
        znacka1.setName("Nezaradene1");
        tovar1.setBrand(znacka1);
        
        tovar1.setImagePath("c:/files/cokolada");
        tovar1.setQuantity(2);
        tovar1.setPrice(1);
        tovar1.setDescription("creative");
        
        tovar2 = new Product();
        tovar2.setId(2L);
        tovar2.setName("topanky");
        
        Category kategoria0 = new Category();
        kategoria0.setId(0L);
        kategoria0.setName("Nezaradene0");      
        tovar2.setCategory(kategoria0);
        
        Brand znacka0 = new Brand();
        znacka0.setId(0L);
        znacka0.setName("Nezaradene0");
        tovar2.setBrand(znacka0);
        
        tovar2.setImagePath("c:/files/topanky");
        tovar2.setQuantity(3);
        tovar2.setPrice(20);
        tovar2.setDescription("popis");
        
        tovar3 = new Product();
        tovar3.setId(3L);
        tovar3.setName("vec");
        
        Category kategoria2 = new Category();
        kategoria2.setId(2L);
        kategoria2.setName("Nezaradene2");      
        tovar3.setCategory(kategoria2);
        
        Brand znacka3 = new Brand();
        znacka3.setId(1L);
        znacka3.setName("Nezaradene3");
        tovar3.setBrand(znacka3);
        
        tovar3.setImagePath("c:/files/vec");
        tovar3.setQuantity(5);
        tovar3.setPrice(10);
        tovar3.setDescription("popis");

        kosik.getProducts().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getProducts().put(tovar2, new SimpleIntegerProperty(4));
        kosik.setTotalPrice(81);
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
        
        kosiKDao.addProductToCart(tovar3, kosik);
        
        Assert.assertTrue(kosik.getProducts().containsKey(tovar3));
        Assert.assertEquals(1, kosik.getProducts().get(tovar3).intValue());
        Assert.assertEquals(91, kosik.getTotalPrice());        
    }
    
    /**
     * Test of dajTovarDoKosika method, of class PamatoviKosikDao.
     * Uz je v kosiku
     */
    @Test
    public void testDajTovarDoKosikaUzJeV() {
        System.out.println("dajTovarDoKosikaUzJeV");
        naplnTestovacieUdaje();
        
        kosiKDao.addProductToCart(tovar2, kosik);
        
        Assert.assertTrue(kosik.getProducts().containsKey(tovar2));
        Assert.assertEquals(5, kosik.getProducts().get(tovar2).intValue());
        Assert.assertEquals(101, kosik.getTotalPrice());        
    }

    /**
     * Test of pocetJednehoTovaruVKosiku method, of class PamatoviKosikDao.
     */
    @Test
    public void testPocetJednehoTovaruVKosiku() {
        System.out.println("pocetJednehoTovaruVKosiku");
        naplnTestovacieUdaje();

        int pocet = kosiKDao.getCartProductQuantity(tovar1, kosik);
        Assert.assertEquals(1, pocet);
    }

    /**
     * Test of nastavTovaruVKosikuPocetKusov method, of class PamatoviKosikDao.
     */
    @Test
    public void testNastavTovaruVKosikuPocetKusov() {
        System.out.println("nastavTovaruVKosikuPocetKusov");
        naplnTestovacieUdaje();

        kosiKDao.setProductQuantity(tovar2, kosik, 10);
        Assert.assertEquals(10, kosik.getProducts().get(tovar2).intValue());
    }

    /**
     * Test of odoberTovarZKosika method, of class PamatoviKosikDao.
     * Je tam jeden kus
     */
    @Test
    public void testOdoberTovarZKosikaJedenKusJeTam() {
        System.out.println("odoberTovarZKosikaJedenKusJeTam");
        naplnTestovacieUdaje();

        kosiKDao.removeProductFromCart(tovar1, kosik);
        IntegerProperty pocet = kosik.getProducts().get(tovar1);

        Assert.assertEquals(null, pocet);        
        Assert.assertEquals(80, kosik.getTotalPrice());
        Assert.assertEquals(1, kosik.getProducts().size());
    }
    
    /**
     * Test of odoberTovarZKosika method, of class PamatoviKosikDao.
     * Je tam viac kusov
     */
    @Test
    public void testOdoberTovarZKosikaViacKusovJeTam() {
        System.out.println("odoberTovarZKosikaViacKusovJeTam");
        naplnTestovacieUdaje();

        kosiKDao.removeProductFromCart(tovar2, kosik);
        IntegerProperty pocet = kosik.getProducts().get(tovar2);

        Assert.assertEquals(new SimpleIntegerProperty(3).getValue(), pocet.getValue());        
        Assert.assertEquals(61, kosik.getTotalPrice());
        Assert.assertEquals(2, kosik.getProducts().size());
    }

    /**
     * Test of dajTovarKosika method, of class PamatoviKosikDao.
     */
    @Test
    public void testDajTovarKosika() {
        System.out.println("dajTovarKosika");
        naplnTestovacieUdaje();

        List<Product> tovar = kosiKDao.getCartProducts(kosik);
        Assert.assertEquals(2, tovar.size());
        Assert.assertTrue(tovar.contains(tovar1));
        Assert.assertTrue(tovar.contains(tovar2));
    }
}
