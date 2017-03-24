package sk.upjs.ics.obchod.managers;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.MemoryCartDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlUserDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.rowmappers.ProductRowMapper;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IUserDao;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.ICartDao;

public class CartManagerTest {

    private ICartManager kosikManager;
    private IUserManager pouzivatelManager;
    private JdbcTemplate jdbcTemplate;    
    private Cart kosik;
    private Product tovar1;
    private Product tovar2;
    private Product tovar3;
    
    public CartManagerTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        ICartDao kosikDao = new MemoryCartDao();
        IProductDao tovarDao = new MysqlProductDao(jdbcTemplate);
        IUserDao pouzivatelDao = new MysqlUserDao(jdbcTemplate);
        pouzivatelManager = new UserManager(pouzivatelDao);
        
        kosikManager = new CartManager(kosikDao, tovarDao, pouzivatelManager);    
        kosik = new Cart();       
        pouzivatelManager.setSignedIn(new SimpleBooleanProperty(true));
    }
    
    @Before
    public void naplnTestovacieUdaje(){
        User p = new User();
        p.setCart(kosik);
        pouzivatelManager.setSignedInUser(p);
        String sql1 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 2, 1, 80, 'popis1', '@../img/1.JPG', 0), "
                        + "('test2', 1, 1, 40, 'popis2', '@../img/2.JPG', 2),"
                        + "('test3', 3, 2, 23, 'popis3', '@../img/3.JPG', 5)";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "INSERT INTO kategoria (nazov) VALUES('Kategoria1'), ('Kategoria2'), ('Kategoria3')";
        jdbcTemplate.execute(sql2);
        
        String sql3 = "INSERT INTO znacka (nazov) VALUES ('Znacka1'), ('Znacka2')";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";
        
        ProductRowMapper mapper  = new ProductRowMapper();
        
        List<Product> tovar = jdbcTemplate.query(sql4, mapper);
              
        tovar1 = tovar.get(0);        
        tovar2 = tovar.get(1);
        tovar3 = tovar.get(2);
        
        kosik.getProducts().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getProducts().put(tovar2, new SimpleIntegerProperty(2));
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql);
        kosik.getProducts().clear();
        pouzivatelManager.logOutUser();
        String sql1 = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql1);
        String sql2 = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql2);
    }
   
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar ma nulovy pocet kusov
     */
    @Test
    public void testPridajTovarDoKosikaNepridaSa() {
        System.out.println("pridajTovarDoKosikaNepridaSa");
                
        boolean pridaloSa = kosikManager.addProductToCart(tovar1);
        int pocetPo = kosik.getProducts().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaru = jdbcTemplate.queryForObject(sql, Long.class);
                
        Assert.assertTrue(!pridaloSa);
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(0), pocetKusovTovaru);        
    }
    
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar ma nenulovy pocet kusov a je uz v kosiku
     */
    @Test
    public void testPridajTovarDoKosikaPridaSaJeTamUz() {
        System.out.println("pridajTovarDoKosikaPridaSaJeTamUz");
                
        boolean pridaloSa = kosikManager.addProductToCart(tovar2);
        int pocetPo = kosik.getProducts().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getProducts().get(tovar2);
                
        Assert.assertTrue(pridaloSa);
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaruPo); 
        Assert.assertEquals(new SimpleIntegerProperty(3).getValue(), pocetVkosikuPo.getValue());
    }
    
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar este nie je v kosiku
     */
    @Test
    public void testPridajTovarDoKosikaPridaSaNieJeTam() {
        System.out.println("pridajTovarDoKosikaPridaSaNieJeTam");
                
        boolean pridaloSa = kosikManager.addProductToCart(tovar3);
        int pocetPo = kosik.getProducts().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 3;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getProducts().get(tovar3);
                
        Assert.assertTrue(pridaloSa);
        Assert.assertEquals(3, pocetPo);
        Assert.assertEquals(new Long(4), pocetKusovTovaruPo); 
        Assert.assertEquals(new SimpleIntegerProperty(1).getValue(), pocetVkosikuPo.getValue());
    }  
    
     /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     * Tovar, ktory ma v kosiku jeden kus
     */    
    @Test
    public void testOdoberTovarZKosikaMaJedenKus() {
        System.out.println("odoberTovarZKosikaMaJedenKus");
                
        kosikManager.removeProductFromCart(tovar1);
        int pocetPo = kosik.getProducts().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);                       
        
        Assert.assertEquals(1, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaruPo);         
    }
    
    /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     * Tovar, ktory ma v kosiku viac kusov
     */    
    @Test
    public void testOdoberTovarZKosikaMaViacKusov() {
        System.out.println("odoberTovarZKosikaMaViacKusov");                
        
        kosikManager.removeProductFromCart(tovar2);
        int pocetPo = kosik.getProducts().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getProducts().get(tovar2);
        
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(3), pocetKusovTovaruPo);   
        Assert.assertEquals(new SimpleIntegerProperty(1).getValue(), pocetVkosikuPo.getValue());
    }
    
    /**
     * Test of dajTovarKosika method, of class DefaultKosikManager.
     */
    @Test
    public void testDajTovarKosika() {
        System.out.println("dajTovarKosika");
        
        List<Product> tovar = kosikManager.getCartProducts();
        int pocet = tovar.size();        
        
        Assert.assertEquals(2, pocet);        
    }

    /**
     * Test of dajPocetTovaruVKosiku method, of class DefaultKosikManager.
     */
    @Test
    public void testDajPocetTovaruVKosiku() {
        System.out.println("dajPocetTovaruVKosiku");        
        
        int pocet = kosikManager.getCartProductQuantity(tovar1);       
        Assert.assertEquals(1, pocet);
    }

    /**
     * Test of pocetTovaruVKosikuProperty method, of class DefaultKosikManager.
     */
    @Test
    public void testPocetTovaruVKosikuProperty() {
        System.out.println("pocetTovaruVKosikuProperty");             
        
        IntegerProperty  pocet = kosikManager.cartProductQuantityProperty(tovar1);      
        Assert.assertEquals(1, pocet.getValue().intValue());
    }

    /**
     * Test of tovarKosikaObservableList method, of class DefaultKosikManager.
     */
    @Test
    public void testTovarKosikaObservableList() {
        System.out.println("tovarKosikaObservableList");
        
        ObservableList<Product> tovar = kosikManager.cartProductsObservableList();
        int pocet = tovar.size();        
        
        Assert.assertEquals(2, pocet); 
    }

    /**
     * Test of vyprazdniKosik method, of class DefaultKosikManager.
     */
    @Test
    public void testVyprazdniKosik() {
        System.out.println("vyprazdniKosik");       
        
        kosikManager.clearCart();
        
        int pocetPo = kosik.getProducts().size();  
        String sql1 = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaru1 = jdbcTemplate.queryForObject(sql1, Long.class);
        String sql2 = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaru2 = jdbcTemplate.queryForObject(sql2, Long.class);                      
        
        Assert.assertEquals(0, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaru1);  
        Assert.assertEquals(new Long(4), pocetKusovTovaru2);
    }
}
