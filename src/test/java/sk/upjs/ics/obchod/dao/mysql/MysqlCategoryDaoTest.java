package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Category;

public class MysqlCategoryDaoTest {
    
    private MysqlCategoryDao kategoriaDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlCategoryDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        kategoriaDao = new MysqlCategoryDao(jdbcTemplate);
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO kategoria (nazov) values ('test1'), ('test2')";
        jdbcTemplate.execute(sql);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql);
    }
    
    /**
     * Test of dajKategorie method, of class MysqlKategoriaDao.
     */
    @Test
    public void testDajKategorie() {
        System.out.println("dajKategorie");
        naplnTestovacieUdaje();
        
        List<Category> kategorie = kategoriaDao.getAll();
        Assert.assertEquals(2, kategorie.size());        
    }

    /**
     * Test of najdiPodlaId method, of class MysqlKategoriaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        naplnTestovacieUdaje();
       
        Category k = kategoriaDao.finById(1L);        
        Assert.assertEquals("test1", k.getName());        
    }
    
    /**
     * Test of najdiPodlaNazvu method, of class MysqlKategoriaDao.
     */
    @Test
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");
        naplnTestovacieUdaje();
        
        Category k = kategoriaDao.findByName("test2");        
        Assert.assertEquals(new Long(2), k.getId());        
    }
    
    /**
     * Test of uloz method, of class MysqlKategoriaDao.
     * Pridaj
     */
    @Test
    public void testUlozPridaj() {
        System.out.println("uloz");        
        Category kategoria = new Category();
        kategoria.setName("skusobna");
       
        Long id = kategoriaDao.saveOrUpdate(kategoria);
        String sql = "SELECT * FROM kategoria";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql, mapper);        
         
        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals(new Long(1), k.getId());
        Assert.assertEquals("skusobna", k.getName());        
    }    
    
    /**
     * Test of uloz method, of class MysqlKategoriaDao.
     * Uprav
     */
    @Test
    public void testUlozUprav() {
        System.out.println("uloz");  
        naplnTestovacieUdaje();
        
        Category kategoria = new Category();
        kategoria.setId(2L);
        kategoria.setName("skusobna");
       
        Long id = kategoriaDao.saveOrUpdate(kategoria);
        String sql = "SELECT * FROM kategoria WHERE id=2";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        Category k = jdbcTemplate.queryForObject(sql, mapper);        
         
        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), k.getId());
        Assert.assertEquals("skusobna", k.getName());        
    }      

    /**
     * Test of odstranKategoriu method, of class MysqlKategoriaDao.
     */
    @Test
    public void testOdstranKategoriu() {
        System.out.println("odstranKategoriu");
        naplnTestovacieUdaje();
        
        Category kategoria = new Category();
        kategoria.setId(1L);        
        kategoriaDao.delete(kategoria);
        
        String sql1 = "SELECT COUNT(*) FROM kategoria"; 
        String sql2 = "SELECT COUNT(*) FROM kategoria WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1)); 
        Assert.assertEquals(pocetSVymazanymId, new Long(0));
    }
}
