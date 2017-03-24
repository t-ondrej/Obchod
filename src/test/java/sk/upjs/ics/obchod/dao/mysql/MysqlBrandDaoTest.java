package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Brand;

public class MysqlBrandDaoTest {
    
    private MysqlBrandDao brandDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlBrandDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        brandDao = new MysqlBrandDao(jdbcTemplate);
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO znacka (nazov) values ('test1'), ('test2')";
        jdbcTemplate.execute(sql);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql);
    }

    /**
     * Test of dajZnacky method, of class MysqlZnackaDao.
     */
    @Test
    public void testDajZnacky() {
        System.out.println("dajZnacky");
        naplnTestovacieUdaje();
                
        List<Brand> znacky = brandDao.getAll();        
        Assert.assertEquals(2, znacky.size());
    }

    /**
     * Test of najdiPodlaId method, of class MysqlZnackaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        naplnTestovacieUdaje();
                
        Brand z = brandDao.findById(1L);        
        Assert.assertEquals("test1", z.getName());        
    }

    /**
     * Test of najdiPodlaNazvu method, of class MysqlZnackaDao.
     */
    @Test
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");    
        naplnTestovacieUdaje();
        
        Brand z = brandDao.findByName("test2");
        Assert.assertEquals(new Long(2), z.getId());
    }
    
    /**
     * Test of uloz method, of class MysqlZnackaDao.
     * Pridaj
     */
    @Test
    public void testUlozPridaj() {
        System.out.println("uloz");
        Brand znacka = new Brand();
        znacka.setName("skusobna");       
        
        Long id = brandDao.saveOrUpdate(znacka);
        String sql = "SELECT * FROM znacka";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        Brand z = jdbcTemplate.queryForObject(sql, mapper); 
         
        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals(new Long(1), z.getId());
        Assert.assertEquals("skusobna", z.getName()); 
    }
    
    /**
     * Test of uloz method, of class MysqlZnackaDao.
     * Uprav
     */
    @Test
    public void testUlozUprav() {
        System.out.println("uloz");
        naplnTestovacieUdaje();
    
        Brand znacka = new Brand();
        znacka.setId(2L);
        znacka.setName("skusobna");       
        
        Long id = brandDao.saveOrUpdate(znacka);
        String sql = "SELECT * FROM znacka WHERE id = 2";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        Brand z = jdbcTemplate.queryForObject(sql, mapper); 
         
        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), z.getId());
        Assert.assertEquals("skusobna", z.getName()); 
    }

    /**
     * Test of odstranZnacku method, of class MysqlZnackaDao.
     */
    @Test
    public void testOdstranZnacku() {
        System.out.println("odstranZnacku");
        naplnTestovacieUdaje();
        
        Brand znacka = new Brand();
        znacka.setId(1L);        
        brandDao.delete(znacka);
        
        String sql1 = "SELECT COUNT(*) FROM znacka"; 
        String sql2 = "SELECT COUNT(*) FROM znacka WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1)); 
        Assert.assertEquals(pocetSVymazanymId, new Long(0));        
    }
}
