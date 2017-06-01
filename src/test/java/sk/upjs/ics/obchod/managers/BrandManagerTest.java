package sk.upjs.ics.obchod.managers;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlBrandDao;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.IBrandDao;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class BrandManagerTest {
    
    private IBrandManager brandManager;
    private JdbcTemplate jdbcTemplate;
    
    public BrandManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        IBrandDao znackaDao = new MysqlBrandDao(jdbcTemplate);
        IProductDao tovarDao = new MysqlProductDao(jdbcTemplate);
        
        brandManager = new BrandManager(znackaDao, tovarDao);     
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
    public void testUpravZnacku() {
        System.out.println("upravZnacku");
        Brand znacka = new Brand(3L, "B3");        
        
        brandManager.update(znacka, "tEsT6");
        String sql = "SELECT * FROM Brand WHERE id = 2";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        Brand z = jdbcTemplate.queryForObject(sql, mapper);
        
        Assert.assertEquals("B2", z.getName());
    }

    @Test
    public void testPridajZnacku() {
        System.out.println("pridajZnacku");
        Brand brand = new Brand(null, "B6");
        
        brandManager.save(brand);
        String sql1 = "SELECT COUNT(*) FROM Brand";
        long brandCount = jdbcTemplate.queryForObject(sql1, long.class);
        
        Assert.assertEquals(new Long(6), brand.getId()); 
        Assert.assertEquals(6l, brandCount);  
        Assert.assertEquals("B6", brand.getName());
    }

    @Test
    public void testExistujeZnackaSNazvom() {
        System.out.println("existujeZnackaSNazvom");
        
        Assert.assertTrue(brandManager.brandExists("B1"));
        Assert.assertTrue(!brandManager.brandExists("TEST5"));
    }

    @Test
    public void testExistujeTovarSoZnackou() {
        System.out.println("existujeTovarSoZnackou");
    
        Assert.assertTrue(brandManager.productOfBrandExists(new Brand(1L, "B1")));
        Assert.assertTrue(!brandManager.productOfBrandExists(new Brand(6L, "B6")));
    }   
}
