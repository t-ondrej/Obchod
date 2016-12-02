package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Kategoria;

public class MysqlKategoriaDaoTest {
    
    private MysqlKategoriaDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlKategoriaDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlKategoriaDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
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
        
        List<Kategoria> kategorie = dao.dajKategorie();
        Assert.assertEquals(2, kategorie.size());        
    }

    /**
     * Test of najdiPodlaId method, of class MysqlKategoriaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        naplnTestovacieUdaje();
       
        Kategoria k = dao.najdiPodlaId(1L);        
        Assert.assertEquals("test1", k.getNazov());        
    }
    
    /**
     * Test of najdiPodlaNazvu method, of class MysqlKategoriaDao.
     */
    @Test
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");
        naplnTestovacieUdaje();
        
        Kategoria k = dao.najdiPodlaNazvu("test2");        
        Assert.assertEquals(new Long(2), k.getId());        
    }
    
    /**
     * Test of uloz method, of class MysqlKategoriaDao.
     */
    @Test
    public void testUloz() {
        System.out.println("uloz");
        Kategoria kategoria = new Kategoria();
        kategoria.setNazov("skusobna");
       
        dao.uloz(kategoria);
        String sql = "SELECT * FROM kategoria";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        Kategoria k = jdbcTemplate.queryForObject(sql, mapper);        
         
        Assert.assertEquals(new Long(1), k.getId());
        Assert.assertEquals("skusobna", k.getNazov());        
    }    
}
