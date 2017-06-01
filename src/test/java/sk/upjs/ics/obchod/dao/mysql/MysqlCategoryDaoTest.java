package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.utils.TestDataProvider;

// DONE
public class MysqlCategoryDaoTest {
    
    private MysqlCategoryDao kategoriaDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlCategoryDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        kategoriaDao = new MysqlCategoryDao(jdbcTemplate);
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
    public void testDajKategorie() {
        System.out.println("dajKategorie");
        
        List<Category> kategorie = kategoriaDao.getAll();
        Assert.assertNotNull(kategorie);
        kategorie.forEach(c -> Assert.assertNotNull(c.getId()));
        Assert.assertEquals("C1", kategorie.get(0).getName());
    }

    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
       
        Category k = kategoriaDao.findById(1L);        
        Assert.assertEquals(new Long(1), k.getId());
        Assert.assertEquals("C1", k.getName());
    }
    
    @Test
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");
        
        Category k = kategoriaDao.findByName("C1");        
        Assert.assertEquals("C1", k.getName());        
    }
    
    @Test
    public void testUlozPridaj() {
        System.out.println("uloz");  
        
        Category kategoria = new Category(null, "skusobna");      
        kategoriaDao.saveOrUpdate(kategoria);
        
        String sql = "SELECT `name` FROM Category WHERE `name` = 'skusobna'";
        String name = jdbcTemplate.queryForObject(sql, String.class);        
         
        Assert.assertNotNull(kategoria);
        Assert.assertEquals("skusobna", name);        
    }    
    

    @Test
    public void testUlozUprav() {
        System.out.println("uloz");  
        
        Category kategoria = new Category(2L, "skusobna2");      
        kategoriaDao.saveOrUpdate(kategoria);
        
        String sql = "SELECT `name` FROM Category WHERE id = 2";
        String name = jdbcTemplate.queryForObject(sql, String.class);        
         
        Assert.assertEquals("skusobna2", name);        
    }      

    @Test
    public void testOdstranKategoriu() {
        System.out.println("odstranKategoriu");
        
        Category kategoria = new Category(3L, null);    
        kategoriaDao.delete(kategoria);
        
        String sql2 = "SELECT COUNT(*) FROM Category WHERE id = 3"; 
        Assert.assertEquals(jdbcTemplate.queryForObject(sql2, Long.class), new Long(0));
    }
}
