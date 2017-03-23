package sk.upjs.ics.obchod.managers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.IKategoriaDao;
import sk.upjs.ics.obchod.dao.ITovarDao;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlTovarDao;
import sk.upjs.ics.obchod.entity.Kategoria;

public class KategoriaManagerTest {
    
    private final IKategoriaManager manager;
    private final JdbcTemplate jdbcTemplate;
    
    public KategoriaManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        
        IKategoriaDao kategoriaDao = new MysqlKategoriaDao(jdbcTemplate);
        ITovarDao tovarDao = new MysqlTovarDao(jdbcTemplate);
        
        manager = new KategoriaManager(kategoriaDao, tovarDao);      
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
        
        boolean ano = manager.existujeKategoriaSNazvom("TEST1");
        boolean nie = manager.existujeKategoriaSNazvom("TEST5");
        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }

    /**
     * Test of upravKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testUpravKategoriu() {
        System.out.println("upravKategoriu");        
        Kategoria kategoria = new Kategoria();
        kategoria.setId(2L);
        kategoria.setNazov("Test2");
        
        manager.upravKategoriu(kategoria, "TEST6");
        String sql = "SELECT * FROM kategoria WHERE id=2";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        Kategoria k = jdbcTemplate.queryForObject(sql, mapper);
        Assert.assertEquals("Test6", k.getNazov());        
    }

    /**
     * Test of pridajKategoriu method, of class DefaultKategoriaManager.
     */
    @Test
    public void testPridajKategoriu() {
        System.out.println("pridajKategoriu");
        Kategoria kategoria = new Kategoria();        
        kategoria.setNazov("TESt4");
        
        Long id = manager.pridajKategoriu(kategoria);
        String sql1 = "SELECT COUNT(*) FROM kategoria";
        Long pocetPo = jdbcTemplate.queryForObject(sql1, Long.class);
        
        String sql2 = "SELECT * FROM kategoria WHERE id=4";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        Kategoria k = jdbcTemplate.queryForObject(sql2, mapper);
        Assert.assertEquals(new Long(4L), id); 
        Assert.assertEquals(new Long(4L), pocetPo);  
        Assert.assertEquals("Test4", k.getNazov());
    }

    /**
     * Test of existujeTovarVKategorii method, of class DefaultKategoriaManager.
     */
    @Test
    public void testExistujeTovarVKategorii() {
        System.out.println("existujeTovarVKategorii");
        Kategoria kategoria1 = new Kategoria();
        kategoria1.setId(1L);
        kategoria1.setNazov("Test1");
        Kategoria kategoria3 = new Kategoria();
        kategoria3.setId(3L);
        kategoria3.setNazov("Test3");
              
        boolean ano = manager.existujeTovarVKategorii(kategoria1);
        boolean nie = manager.existujeTovarVKategorii(kategoria3);        
        Assert.assertTrue(ano);
        Assert.assertTrue(!nie);
    }    
}
