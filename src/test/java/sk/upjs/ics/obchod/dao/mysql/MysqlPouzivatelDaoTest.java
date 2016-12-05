package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.rowmappers.PouzivatelRowMapper;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class MysqlPouzivatelDaoTest {
    
    private MysqlPouzivatelDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlPouzivatelDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlPouzivatelDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        
        Pouzivatel p1 = new Pouzivatel();
        
        p1.setPrihlasovacieMeno("test1");
        p1.setEmail("test1@test.sk");
        p1.setPassword("test1");
        p1.setPoslednePrihlasenie(LocalDate.now());
        p1.setJeAdministrator(true);
        Kosik k1 = new Kosik();
        p1.setKosik(k1);
        
        Pouzivatel p2 = new Pouzivatel();
        p2.setPrihlasovacieMeno("test2");
        p2.setEmail("test2@test.sk");
        p2.setPassword("test2");
        p2.setPoslednePrihlasenie(LocalDate.now());
        p2.setJeAdministrator(false);
        Kosik k2 = new Kosik();
        p2.setKosik(k2);
        
        jdbcTemplate.update(sql, p1.getPrihlasovacieMeno(), p1.getPasswordHash(), 
                p1.getSol(), p1.getEmail(), p1.getPoslednePrihlasenie(), p1.isJeAdministrator());
        jdbcTemplate.update(sql, p2.getPrihlasovacieMeno(), p2.getPasswordHash(), 
                p2.getSol(), p2.getEmail(), p2.getPoslednePrihlasenie(), p2.isJeAdministrator());
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE pouzivatel;";
        jdbcTemplate.execute(sql);
    }
   
    /**
     * Test of dajPouzivatelov method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelov() {        
        System.out.println("dajPouzivatelov");
        naplnTestovacieUdaje();
        
        List<Pouzivatel> pouzivatelia = dao.dajPouzivatelov();
        Assert.assertEquals(2, pouzivatelia.size());        
    }
    
    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaMena() {
        System.out.println("dajPouzivatelaPodlaMena");
        naplnTestovacieUdaje();
        
        Pouzivatel pouzivatel = dao.dajPouzivatela("test1");
        Assert.assertEquals(new Long(1), pouzivatel.getId());
    }
    
    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaId() {
        System.out.println("dajPouzivatelaPodlaId");
        naplnTestovacieUdaje();
                
        Pouzivatel p = dao.dajPouzivatela(2L);
        Assert.assertEquals("test2", p.getPrihlasovacieMeno());
        Assert.assertEquals("test2@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());        
    }   

    /**
     * Test of pridajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testPridajPouzivatela() {
        System.out.println("pridajPouzivatela");
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setPrihlasovacieMeno("test");
        pouzivatel.setEmail("test@test.sk");
        pouzivatel.setPassword("test");
        pouzivatel.setPoslednePrihlasenie(LocalDate.now());
        pouzivatel.setJeAdministrator(false);
        Kosik k = new Kosik();
        pouzivatel.setKosik(k);
        
        Long id = dao.pridajPouzivatela(pouzivatel);
        String sql = "SELECT * FROM pouzivatel";
        Pouzivatel p = jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper()); 
        
        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals("test", p.getPrihlasovacieMeno());
        Assert.assertEquals("test@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());        
    }

    /**
     * Test of odstranPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testOdstranPouzivatela() {
        System.out.println("odstranPouzivatela");
        naplnTestovacieUdaje();
        
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setId(1L);
        dao.odstranPouzivatela(pouzivatel);
        String sql1 = "SELECT COUNT(*) FROM pouzivatel"; 
        String sql2 = "SELECT COUNT(*) FROM pouzivatel WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1)); 
        Assert.assertEquals(pocetSVymazanymId, new Long(0));       
    }    
}
