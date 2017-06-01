package sk.upjs.ics.obchod.managers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlBillDao;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IBillDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class BillManagerTest {
    
    private BillManager manager;
    private JdbcTemplate jdbcTemplate;
    
    public BillManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        IBillDao fakturaDao = new MysqlBillDao(jdbcTemplate);
        manager = new BillManager(fakturaDao);      
    }  
    
    @BeforeClass
    public static void setUp() {
        TestDataProvider.insertTestData();
    }
    
    @AfterClass
    public static void tearDown() {
        TestDataProvider.clearTestData();
    }
    
    @Test
    public void testVytvorFakturu() {
        System.out.println("vytvorFakturu");
        
        Cart cart = new Cart();
        cart.setProductDao(new MysqlProductDao(jdbcTemplate));
        
        Product p3 = new Product(3L, "P3", new Brand(1L, "B1"), new Category(2L, "C2"), 3, "desc3", "@../img/3.JPG", 2);        
        Product p4 = new Product(4L, "P4", new Brand(2L, "B2"), new Category(2L, "C2"), 3, "desc4", "@../img/4.JPG", 3);    
        
        cart.getProducts().put(p3, new SimpleIntegerProperty(1));
        cart.getProducts().put(p4, new SimpleIntegerProperty(2));
        
        Account account = new Account(2L, "dandiV", "hash", "salt", cart, new Person());        
        
        manager.createBill(account);
        
        String sql1 = "SELECT COUNT(*) FROM Bill"; 
        Long billCount = jdbcTemplate.queryForObject(sql1, long.class); 
        
        String sql2 = "SELECT COUNT(*) FROM Bill_Product"; 
        long billProductCount = jdbcTemplate.queryForObject(sql2, long.class);
        
        String sql3 = "SELECT SUM(quantity) FROM Bill_Product WHERE product_name = 'P3'";
        Long p3Quant = jdbcTemplate.queryForObject(sql3, Long.class);
        
        String sql4 = "SELECT SUM(quantity) FROM Bill_Product WHERE product_name = 'P4'";
        Long p4Quant = jdbcTemplate.queryForObject(sql4, Long.class);
        
        ObservableMap<Product, IntegerProperty> cartProducts = cart.getProducts();
        
        Assert.assertEquals(new Long(6), billCount);
        Assert.assertEquals(10L, billProductCount);
        Assert.assertEquals(new Long(8), p3Quant);
        Assert.assertEquals(new Long(10), p4Quant);
        Assert.assertEquals(0, cartProducts.size());
    }
}
