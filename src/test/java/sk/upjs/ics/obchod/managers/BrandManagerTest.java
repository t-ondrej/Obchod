package sk.upjs.ics.obchod.managers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlBrandDao;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.IBrandDao;

public class BrandManagerTest {
    
    private IBrandManager manager;
    private JdbcTemplate jdbcTemplate;
    
    public BrandManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        IBrandDao znackaDao = new MysqlBrandDao(jdbcTemplate);
        IProductDao tovarDao = new MysqlProductDao(jdbcTemplate);
        
        manager = new BrandManager(znackaDao, tovarDao);
        
    }

    @Before
    public void naplnTestovacieUdaje(){
        String sql = "INSERT INTO znacka (nazov) values ('Test1'), ('Test2'), ('Test3')";
        jdbcTemplate.execute(sql);
        
        String sql1 = "INSERT INTO kategoria (nazov) values ('1'),('2')";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " VALUES ('test11', 2, 1, 80, 'dobre', '@../img/1.JPG', 2), "
                + "('test12', 1, 1, 40, 'skvele', '@../img/2.JPG', 0)";
        jdbcTemplate.execute(sql2);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql);
        String sql1 = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql1);
        String sql2 = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql2);
    }
    
    /**
     * Test of upravZnacku method, of class DefaultZnackaManager.
     */
    @Test
    public void testUpravZnacku() {
        System.out.println("upravZnacku");
        Brand znacka = new Brand();        
        znacka.setId(2L);
        znacka.setName("Test2");
        
        manager.update(znacka, "TEST6");
        String sql = "SELECT * FROM znacka WHERE id=2";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        Brand z = jdbcTemplate.queryForObject(sql, mapper);
        Assert.assertEquals("Test6", z.getName());
    }

    /**
     * Test of pridajZnacku method, of class DefaultZnackaManager.
     */
    @Test
    public void testPridajZnacku() {
        System.out.println("pridajZnacku");
        Brand znacka = new Brand();
        znacka.setName("TESt4");
        
        Long id = manager.save(znacka);
        String sql1 = "SELECT COUNT(*) FROM znacka";
        Long pocetPo = jdbcTemplate.queryForObject(sql1, Long.class);
        
        String sql2 = "SELECT * FROM znacka WHERE id=4";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        Brand z = jdbcTemplate.queryForObject(sql2, mapper);
        Assert.assertEquals(new Long(4L), id); 
        Assert.assertEquals(new Long(4L), pocetPo);  
        Assert.assertEquals("Test4", z.getName());
    }

    /**
     * Test of existujeZnackaSNazvom method, of class DefaultZnackaManager.
     */
    @Test
    public void testExistujeZnackaSNazvom() {
        System.out.println("existujeZnackaSNazvom");
        
        boolean ano = manager.brandExists("TEST1");
        boolean nie = manager.brandExists("TEST5");
        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }

    /**
     * Test of existujeTovarSoZnackou method, of class DefaultZnackaManager.
     */
    @Test
    public void testExistujeTovarSoZnackou() {
        System.out.println("existujeTovarSoZnackou");
        Brand znacka1 = new Brand();        
        znacka1.setId(1L);
        znacka1.setName("Test2");
        Brand znacka3 = new Brand();
        znacka3.setId(3L);
        znacka3.setName("Test3");
              
        boolean ano = manager.productOfBrandExists(znacka1);
        boolean nie = manager.productOfBrandExists(znacka3);        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }
    
}
