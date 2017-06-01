package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.utils.TestDataProvider;


// DONE
public class MysqlBrandDaoTest {
    
    private MysqlBrandDao brandDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlBrandDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        brandDao = new MysqlBrandDao(jdbcTemplate);
    }
    
    @BeforeClass
    public static void setUp() {
        TestDataProvider.insertTestData();
    }
    
    @AfterClass
    public static void tearDown() {
        TestDataProvider.clearTestData();
    }  

    /**
     * Test of dajZnacky method, of class MysqlZnackaDao.
     */
    @Test // DONE
    public void testDajZnacky() {
        System.out.println("dajZnacky");
                
        List<Brand> znacky = brandDao.getAll();      
        znacky.forEach(z -> Assert.assertNotNull(z.getId()));              
        Assert.assertNotNull(znacky);
        Assert.assertEquals("B1", znacky.get(0).getName());
    }

    /**
     * Test of najdiPodlaId method, of class MysqlZnackaDao.
     */
    @Test // DONE
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
                
        Brand z = brandDao.findById(1L);        
        Assert.assertEquals(new Long(1), z.getId());        
    }

    /**
     * Test of najdiPodlaNazvu method, of class MysqlZnackaDao.
     */
    @Test // DONE
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");    
        
        Brand z = brandDao.findByName("B4");
        Assert.assertEquals("B4", z.getName());
    }
    
    /**
     * Test of uloz method, of class MysqlZnackaDao.
     * Pridaj
     */
    @Test // DONE
    public void testUlozPridaj() {
        System.out.println("uloz");
        Brand znacka = new Brand(null, "testUlozPridaj");   
        
        brandDao.saveOrUpdate(znacka);
        Assert.assertNotNull(znacka.getId());
    }
    
    /**
     * Test of uloz method, of class MysqlZnackaDao.
     * Uprav
     */
    @Test  // DONE
    public void testUlozUprav() {
        System.out.println("uloz");
 
        Brand znacka = new Brand(2L, "skusobna");   
        
        brandDao.saveOrUpdate(znacka);
        String sql = "SELECT `name` FROM Brand WHERE id = 2";
        String noveMeno = jdbcTemplate.queryForObject(sql, String.class);       
        
        Assert.assertEquals("skusobna", noveMeno); 
    }

    /**
     * Test of odstranZnacku method, of class MysqlZnackaDao.
     */
    @Test // DONE
    public void testOdstranZnacku() {
        System.out.println("odstranZnacku");
        
        String sql = "SELECT COUNT(*) FROM Brand";
        Long pocetPredVymazanim = jdbcTemplate.queryForObject(sql, Long.class);       
        
        Brand znacka = new Brand(3L, "");    
        brandDao.delete(znacka);                    
        
        Long pocetPoVymazani = jdbcTemplate.queryForObject(sql, Long.class);
        
        Long rozdiel = pocetPredVymazanim - pocetPoVymazani;
        
        Assert.assertEquals(new Long(1), rozdiel);     
    }
}
