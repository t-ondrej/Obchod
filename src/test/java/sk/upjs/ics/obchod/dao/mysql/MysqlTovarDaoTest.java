package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlTovarDaoTest {

    private MysqlTovarDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlTovarDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlTovarDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 2, 1, 80, 'dobre', '@../img/1.JPG', 2), "
                        + "('test2', 1, 1, 40, 'skvele', '@../img/2.JPG', 0)";
        jdbcTemplate.execute(sql);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql);
    }
      
    /**
     * Test of dajTovary method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovary() {
        System.out.println("dajTovary");
        naplnTestovacieUdaje();
        
        List<Tovar> tovary = dao.dajTovary();
        Assert.assertEquals(2, tovary.size());
    }

    /**
     * Test of dajTovarPodlaKategorie method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaKategorie() {
        System.out.println("dajTovarPodlaKategorie");
        naplnTestovacieUdaje();       
        
        Kategoria kategoria = new Kategoria();
        kategoria.setId(1L);
        kategoria.setNazov("Kategoria1Test");
        
        List<Tovar> tovary = dao.dajTovarPodlaKategorie(kategoria);        
        Assert.assertEquals(1, tovary.size());
    }   
    
    /**
     * Test of dajTovarPodlaNazvu method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaNazvu() {
        System.out.println("dajTovarPodlaNazvu");
        naplnTestovacieUdaje();      
        
        Tovar tovar = dao.dajTovarPodlaNazvu("test1");
        Assert.assertEquals(new Long(1), tovar.getId());     
    }
      
    /**
     * Test of dajTovarPodlaZnacky method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaZnacky() {
        System.out.println("dajTovarPodlaZnacky");
        naplnTestovacieUdaje(); 
        
        Znacka znacka = new Znacka();
        znacka.setId(1L);
        znacka.setNazov("Znacka1Test");
        
        List<Tovar> tovary = dao.dajTovarPodlaZnacky(znacka);
        Assert.assertEquals(2, tovary.size());
    }
    
    /**
     * Test of pridajTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testPridajTovar() {
        System.out.println("pridajTovar");        
        Tovar tovar = new Tovar();
        tovar.setIdKategoria(3L);
        tovar.setIdZnacka(4L);
        tovar.setNazov("nazov1");
        tovar.setCena(42);
        tovar.setObrazokUrl("@../img/3.JPG");
        tovar.setPopis("ok");
        tovar.setPocetKusov(6);
        
        dao.pridajTovar(tovar);
        String sql = "SELECT * FROM tovar";
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        Tovar t = jdbcTemplate.queryForObject(sql, mapper); 

        Assert.assertEquals(new Long(1), t.getId());
        Assert.assertEquals(new Long(3), t.getIdKategoria());
        Assert.assertEquals(new Long(4), t.getIdZnacka());
        Assert.assertEquals("nazov1", t.getNazov());
        Assert.assertEquals(42, t.getCena());
        Assert.assertEquals("@../img/3.JPG", t.getObrazokUrl());
        Assert.assertEquals("ok", t.getPopis());
        Assert.assertEquals(6, t.getPocetKusov());        
    }

    /**
     * Test of odstranTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");
        naplnTestovacieUdaje();
        
        dao.odstranTovar(1L);
        String sql1 = "SELECT COUNT(*) FROM tovar"; 
        String sql2 = "SELECT COUNT(*) FROM tovar WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1)); 
        Assert.assertEquals(pocetSVymazanymId, new Long(0));        
    }

    /**
     * Test of najdiPodlaId method, of class MysqlTovarDao.
     */
    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");
        naplnTestovacieUdaje();        
        
        Tovar t = dao.najdiPodlaId(1L);       
        Assert.assertEquals(new Long(2), t.getIdKategoria());
        Assert.assertEquals(new Long(1), t.getIdZnacka());
        Assert.assertEquals("test1", t.getNazov());
        Assert.assertEquals(80, t.getCena());
        Assert.assertEquals("@../img/1.JPG", t.getObrazokUrl());
        Assert.assertEquals("dobre", t.getPopis());
        Assert.assertEquals(2, t.getPocetKusov()); 
    }

    /**
     * Test of nastavTovaruPocetKusov method, of class MysqlTovarDao.
     */
    @Test
    public void testNastavTovaruPocetKusov() {
        System.out.println("nastavTovaruPocetKusov");
        naplnTestovacieUdaje();
        
        dao.nastavTovaruPocetKusov(1L, 9);
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaru = jdbcTemplate.queryForObject(sql, Long.class);
        Assert.assertEquals(new Long(9), pocetKusovTovaru);       
    }

    /**
     * Test of dajPocetTovaru method, of class MysqlTovarDao.
     */
    @Test
    public void testDajPocetTovaru() {
        System.out.println("dajPocetTovaru");
        naplnTestovacieUdaje();
        
        int pocet = dao.dajPocetTovaru(1L);
        Assert.assertEquals(2, pocet);       
    } 
}
