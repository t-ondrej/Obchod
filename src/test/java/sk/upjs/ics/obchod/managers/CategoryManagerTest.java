package sk.upjs.ics.obchod.managers;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlCategoryDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.dao.ICategoryDao;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class CategoryManagerTest {
    
    private final ICategoryManager manager;
    private final JdbcTemplate jdbcTemplate;
    
    public CategoryManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        ICategoryDao kategoriaDao = new MysqlCategoryDao(jdbcTemplate);
        IProductDao tovarDao = new MysqlProductDao(jdbcTemplate);
        
        manager = new CategoryManager(kategoriaDao, tovarDao);      
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
     * Test of existujeKategoriaSNazvom method, of class DefaultKategoriaManager.
     */
    @Test
    public void testExistujeKategoriaSNazvom() {
        System.out.println("existujeKategoriaSNazvom");
        
        Assert.assertTrue(manager.categoryExists("C1"));
        Assert.assertTrue(!manager.categoryExists("C7"));
    }

    /**
     * Test of upravKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testUpravKategoriu() {
        System.out.println("upravKategoriu");        
        Category kategoria = new Category(3L, "C3");
        
        manager.update(kategoria, "C33");
        String sql = "SELECT * FROM Category WHERE id = 3";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql, mapper);
        Assert.assertEquals("C33", k.getName());        
    }

    // UNCOMPLETE
    /**
     * Test of pridajKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testPridajKategoriu() {
        System.out.println("pridajKategoriu");
        Category category = new Category(null, "TESt4");        
        
        manager.save(category);
        String sql1 = "SELECT COUNT(*) FROM Category";
        int categoryCount = jdbcTemplate.queryForObject(sql1, int.class);
        
        String sql2 = "SELECT * FROM Category WHERE id = 4";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql2, mapper);
        
        Assert.assertNotNull(category.getId()); 
        Assert.assertEquals(6, categoryCount);  
        Assert.assertEquals("C4", k.getName());
    }

    /**
     * Test of existujeTovarVKategorii method, of class DefaultKategoriaManager.
     */
    @Test
    public void testExistujeTovarVKategorii() {
        System.out.println("existujeTovarVKategorii");
        Category kategoria1 = new Category(1L, "C1");
        Category kategoria3 = new Category(3L, "C3");
                  
        Assert.assertTrue(manager.productsOfCategoryExists(kategoria1));
        Assert.assertTrue(!manager.productsOfCategoryExists(kategoria3));
    }    
}
