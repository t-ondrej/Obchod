package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class MysqlBillDaoTest {
    
    private MysqlBillDao billDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlBillDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        billDao = new MysqlBillDao(jdbcTemplate);
    }
    
    @BeforeClass
    public static void setUp() {
        TestDataProvider.insertTestData();
    }
    
    @AfterClass
    public static void tearDown() {
        TestDataProvider.clearTestData();
    }
    
    public void naplnTestovacieUdaje(){
      /*  String[] sql = {
                "INSERT INTO Bill(account_id, total_price, purchase_date) VALUES"
                + "(1, 100, date_add(now(), interval -3 minute)),"
                + "(2, 55, date_add(now(), interval -5 minute)), "
                + "(3, 64, date_add(now(), interval -1 year)), "
                + "(3, 60, date_add(now(), interval -1 week)), "
                + "(3, 65, date_add(now(), interval -1 day)), "
                + "(3, 60, date_add(now(), interval -1 month)); ",
                
                "INSERT INTO Category (`name`) VALUES ('EleGiggle'); ",
        
                "INSERT INTO Brand (`name`) VALUES ('4Head'); ",
        
                "INSERT INTO Product (`name`, category_id, brand_id, price, description, image_path, quantity)"
                + " values ('test1', 1, 1, 80, 'High quality', '@../img/1.JPG', 2); ",
    
                "INSERT INTO Bill_Product(bill_id, product_name, category_name, "
                + "brand_name, quantity, price) VALUES(4, 'Product1', 'C1', 'B1', 6, 80);"};
        
        jdbcTemplate.batchUpdate(sql);*/
    }
    
    public void vymazTestovacieUdaje(){
        String sql[] = {
                "TRUNCATE TABLE Bill; ",
                "TRUNCATE TABLE Bill_Product; ",
                "TRUNCATE TABLE Product; ",
                "TRUNCATE TABLE Category; ",
                "TRUNCATE TABLE Brand;" };

        jdbcTemplate.batchUpdate(sql);
    }
    
    // Done
    @Test
    public void testDajFaktury() {
        System.out.println("dajFaktury");
             
        List<Bill> faktury = billDao.getAll();   
        faktury.forEach(f -> Assert.assertNotNull(f.getId()));
        Assert.assertNotNull(faktury);     
    }    
    
    // Done
    @Test
    public void testDajFakturyZaPoslednyDen() {
        System.out.println("dajFakturyZaPoslednyDen");
        
        List<Bill> faktury = billDao.getBillsForLastDay();         
        Assert.assertTrue(faktury.size() > 0); 
    }

    // Done
    @Test
    public void testDajFakturyZaPoslednyTyzden() {
        System.out.println("dajFakturyZaPoslednyTyzden");
        
        List<Bill> faktury = billDao.getBillsForLastWeek();       
        Assert.assertTrue(faktury.size() > 1);         
    }

// Done
    @Test
    public void testDajFakturyZaPoslednyMesiac() {
        System.out.println("dajFakturyZaPoslednyMesiac");
        
        List<Bill> faktury = billDao.getBillsForLastMonth();       
        Assert.assertTrue(faktury.size() > 2);        
    }

// Done
    @Test
    public void testDajFakturyZaPoslednyRok() {
        System.out.println("dajFakturyZaPoslednyRok");
        
        List<Bill> faktury = billDao.getBillsForLastYear();       
        Assert.assertTrue(faktury.size() > 4);        
    }

// Done
    @Test
    public void testPridajFakturu() {
        System.out.println("pridajFakturu");   
        
        Bill bill = new Bill(null, 3, 50, LocalDateTime.of(1995, Month.MARCH, 1, 2, 30));        
        billDao.saveOrUpdate(bill);      
        Assert.assertNotNull(bill.getId());
    }

// Done
    @Test
    public void testOdstranFakturu() {
        System.out.println("odstranFakturu");
        
        Bill faktura = new Bill(1L, 1L, 1, LocalDateTime.now()); 
        billDao.delete(faktura);
        
        String sql2 = "SELECT COUNT(*) FROM Bill WHERE id = 1";         
        Assert.assertEquals(jdbcTemplate.queryForObject(sql2, Long.class), new Long(0));
    }    

  // Done  
    @Test
    public void testPridajTovarFakture() {
        System.out.println("pridajTovarFakture");
        
        Product product = new Product(2L, "NazovTovaru", new Brand(1L, "NazovZnacky"), 
                new Category(1L, "NazovKategorie"), 150, "", "", 1); 
        
        Bill bill = new Bill(5L, 1L, 150, LocalDateTime.now());               
        billDao.addProductToBill(product, bill, 3);
        
        String sql2 = "SELECT quantity FROM Bill_Product WHERE product_name = 'NazovTovaru' and bill_id = 5";                      
        Assert.assertEquals(new Long(3), jdbcTemplate.queryForObject(sql2, Long.class));        
    }

  // Done  
    @Test
    public void testDajTovarFaktury() {
        System.out.println("dajTovarFaktury");
        
        Bill bill = new Bill();
        bill.setId(4L);
        
        List<Product> products = billDao.getBillProducts(bill);

        Assert.assertEquals("P4", products.get(0).getName()); 
        Assert.assertEquals("C2", products.get(0).getCategory().getName());
        Assert.assertEquals("B3", products.get(0).getBrand().getName());
        Assert.assertEquals(4, products.get(0).getPrice()); 
        Assert.assertEquals(5, products.get(0).getQuantity());        
    }
}
