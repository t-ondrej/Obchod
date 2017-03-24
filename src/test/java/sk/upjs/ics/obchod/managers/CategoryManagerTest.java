package sk.upjs.ics.obchod.managers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlCategoryDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.dao.ICategoryDao;
import sk.upjs.ics.obchod.dao.IProductDao;

public class CategoryManagerTest {
    
    private final ICategoryManager manager;
    private final JdbcTemplate jdbcTemplate;
    
    public CategoryManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        ICategoryDao kategoriaDao = new MysqlCategoryDao(jdbcTemplate);
        IProductDao tovarDao = new MysqlProductDao(jdbcTemplate);
        
        manager = new CategoryManager(kategoriaDao, tovarDao);      
    }
    
    @Before
    public void naplnTestovacieUdaje(){
        String sql = "INSERT INTO kategoria (nazov) values ('Test1'), ('Test2'), ('Test3')";
        jdbcTemplate.execute(sql);
        
        String sql1 = "INSERT INTO znacka (nazov) values ('1')";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " VALUES ('test11', 2, 1, 80, 'dobre', '@../img/1.JPG', 2), "
                + "('test12', 1, 1, 40, 'skvele', '@../img/2.JPG', 0)";
        jdbcTemplate.execute(sql2);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql);
        String sql1 = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql1);
        String sql2 = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql2);
    }

    /**
     * Test of existujeKategoriaSNazvom method, of class DefaultKategoriaManager.
     */
    @Test
    public void testExistujeKategoriaSNazvom() {
        System.out.println("existujeKategoriaSNazvom");
        
        boolean ano = manager.categoryExists("TEST1");
        boolean nie = manager.categoryExists("TEST5");
        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }

    /**
     * Test of upravKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testUpravKategoriu() {
        System.out.println("upravKategoriu");        
        Category kategoria = new Category();
        kategoria.setId(2L);
        kategoria.setName("Test2");
        
        manager.update(kategoria, "TEST6");
        String sql = "SELECT * FROM kategoria WHERE id=2";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql, mapper);
        Assert.assertEquals("Test6", k.getName());        
    }

    /**
     * Test of pridajKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testPridajKategoriu() {
        System.out.println("pridajKategoriu");
        Category kategoria = new Category();        
        kategoria.setName("TESt4");
        
        Long id = manager.add(kategoria);
        String sql1 = "SELECT COUNT(*) FROM kategoria";
        Long pocetPo = jdbcTemplate.queryForObject(sql1, Long.class);
        
        String sql2 = "SELECT * FROM kategoria WHERE id=4";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql2, mapper);
        Assert.assertEquals(new Long(4L), id); 
        Assert.assertEquals(new Long(4L), pocetPo);  
        Assert.assertEquals("Test4", k.getName());
    }

    /**
     * Test of existujeTovarVKategorii method, of class DefaultKategoriaManager.
     */
    @Test
    public void testExistujeTovarVKategorii() {
        System.out.println("existujeTovarVKategorii");
        Category kategoria1 = new Category();
        kategoria1.setId(1L);
        kategoria1.setName("Test1");
        Category kategoria3 = new Category();
        kategoria3.setId(3L);
        kategoria3.setName("Test3");
              
        boolean ano = manager.productsOfCategoryExists(kategoria1);
        boolean nie = manager.productsOfCategoryExists(kategoria3);        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }    
}
