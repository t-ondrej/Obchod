package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlZnackaDaoTest {
    
    private MysqlZnackaDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlZnackaDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlZnackaDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
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
                
        List<Znacka> znacky = dao.dajZnacky();        
        Assert.assertEquals(2, znacky.size());
    }

    /**
     * Test of najdiPodlaId method, of class MysqlZnackaDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        naplnTestovacieUdaje();
                
        Znacka z = dao.najdiPodlaId(1L);        
        Assert.assertEquals("test1", z.getNazov());        
    }

    /**
     * Test of najdiPodlaNazvu method, of class MysqlZnackaDao.
     */
    @Test
    public void testNajdiPodlaNazvu() {
        System.out.println("najdiPodlaNazvu");
        naplnTestovacieUdaje();        
        
        Znacka z = dao.najdiPodlaNazvu("test2");
        Assert.assertEquals(new Long(2), z.getId());
    }
    
    /**
     * Test of uloz method, of class MysqlZnackaDao.
     */
    @Test
    public void testUloz() {
        System.out.println("uloz");
        Znacka znacka = new Znacka();
        znacka.setNazov("skusobna");       
        
        dao.uloz(znacka);
        String sql = "SELECT * FROM znacka";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        Znacka z = jdbcTemplate.queryForObject(sql, mapper); 
         
        Assert.assertEquals(new Long(1), z.getId());
        Assert.assertEquals("skusobna", z.getNazov()); 
    }
}
