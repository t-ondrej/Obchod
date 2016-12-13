package sk.upjs.ics.obchod.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Znacka;

public class DefaultZnackaManagerTest {
    
    private ZnackaManager manager;
    private JdbcTemplate jdbcTemplate;
    
    public DefaultZnackaManagerTest() {
        manager = new DefaultZnackaManager(true);
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
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
        Znacka znacka = new Znacka();        
        znacka.setId(2L);
        znacka.setNazov("Test2");
        
        manager.upravZnacku(znacka, "TEST6");
        String sql = "SELECT * FROM znacka WHERE id=2";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        Znacka z = jdbcTemplate.queryForObject(sql, mapper);
        Assert.assertEquals("Test6", z.getNazov());
    }

    /**
     * Test of pridajZnacku method, of class DefaultZnackaManager.
     */
    @Test
    public void testPridajZnacku() {
        System.out.println("pridajZnacku");
        Znacka znacka = new Znacka();
        znacka.setNazov("TESt4");
        
        Long id = manager.pridajZnacku(znacka);
        String sql1 = "SELECT COUNT(*) FROM znacka";
        Long pocetPo = jdbcTemplate.queryForObject(sql1, Long.class);
        
        String sql2 = "SELECT * FROM znacka WHERE id=4";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        Znacka z = jdbcTemplate.queryForObject(sql2, mapper);
        Assert.assertEquals(new Long(4L), id); 
        Assert.assertEquals(new Long(4L), pocetPo);  
        Assert.assertEquals("Test4", z.getNazov());
    }

    /**
     * Test of existujeZnackaSNazvom method, of class DefaultZnackaManager.
     */
    @Test
    public void testExistujeZnackaSNazvom() {
        System.out.println("existujeZnackaSNazvom");
        
        boolean ano = manager.existujeZnackaSNazvom("TEST1");
        boolean nie = manager.existujeZnackaSNazvom("TEST5");
        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }

    /**
     * Test of existujeTovarSoZnackou method, of class DefaultZnackaManager.
     */
    @Test
    public void testExistujeTovarSoZnackou() {
        System.out.println("existujeTovarSoZnackou");
        Znacka znacka1 = new Znacka();        
        znacka1.setId(1L);
        znacka1.setNazov("Test2");
        Znacka znacka3 = new Znacka();
        znacka3.setId(3L);
        znacka3.setNazov("Test3");
              
        boolean ano = manager.existujeTovarSoZnackou(znacka1);
        boolean nie = manager.existujeTovarSoZnackou(znacka3);        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }
    
}
