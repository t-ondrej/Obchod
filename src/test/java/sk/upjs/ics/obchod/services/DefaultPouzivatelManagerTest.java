package sk.upjs.ics.obchod.services;

import java.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.rowmappers.PouzivatelRowMapper;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class DefaultPouzivatelManagerTest {
    
    private PouzivatelManager manager;
    private JdbcTemplate jdbcTemplate; 
    
    public DefaultPouzivatelManagerTest(){
        manager = DefaultPouzivatelManager.INSTANCETEST;
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        
        Pouzivatel p1 = new Pouzivatel();
        
        p1.setPrihlasovacieMeno("test1");
        p1.setEmail("test1@test.sk");
        p1.setPassword("test1");
        p1.setPoslednePrihlasenie(LocalDate.of(2016,11,3));
        p1.setJeAdministrator(true);
        Kosik k1 = new Kosik();
        p1.setKosik(k1);       
        
        Pouzivatel p2 = new Pouzivatel();
        p2.setPrihlasovacieMeno("test2");
        p2.setEmail("test2@test.sk");
        p2.setPassword("test2");
        p2.setPoslednePrihlasenie(LocalDate.of(2016,10,1));
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
     * Test of prihlasPouzivatela method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testPrihlasPouzivatela() {
        System.out.println("prihlasPouzivatela");
        naplnTestovacieUdaje();
        
        boolean podariloSa = manager.prihlasPouzivatela("test1", "test1");
        
        Assert.assertEquals(true, podariloSa);
        Assert.assertEquals(new Long(1), manager.getAktivnyPouzivatel().getId());
        Assert.assertEquals(true, manager.isPrihlaseny().getValue());        
    }

    /**
     * Test of registrujPouzivatela method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testRegistrujPouzivatela() {
        System.out.println("registrujPouzivatela");
        naplnTestovacieUdaje();
        
        manager.registrujPouzivatela("test3", "test3", "test3@test.sk");
        String sql = "SELECT * FROM pouzivatel WHERE prihlasovacie_meno = 'test3'";
        Pouzivatel p = jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper()); 
        
        Assert.assertEquals(new Long(3), p.getId());
        Assert.assertEquals("test3", p.getPrihlasovacieMeno());
        Assert.assertEquals("test3@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());   
    }

    /**
     * Test of jeVolneMeno method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testJeVolneMeno() {
        System.out.println("jeVolneMeno");
        naplnTestovacieUdaje();
        
        boolean volne = manager.jeVolneMeno("testtest");
        boolean obsadene = manager.jeVolneMeno("test1");
        Assert.assertEquals(true, volne); 
        Assert.assertEquals(false, obsadene); 
    }  
}
