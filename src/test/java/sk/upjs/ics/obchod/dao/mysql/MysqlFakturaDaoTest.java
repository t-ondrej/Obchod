package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Faktura;

public class MysqlFakturaDaoTest {
    
    private MysqlFakturaDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlFakturaDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlFakturaDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(1, 100, now()),(2, 55, now())";
        jdbcTemplate.execute(sql);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE faktura;";
        jdbcTemplate.execute(sql);
    }
    
    /**
     * Test of dajFaktury method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFaktury() {
        System.out.println("dajFaktury");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = dao.dajFaktury();        
        Assert.assertEquals(2, faktury.size());       
    }

    /**
     * Test of pridajFakturu method, of class MysqlFakturaDao.
     */
    @Test
    public void testPridajFakturu() {
        System.out.println("pridajFakturu");        
        Faktura faktura = new Faktura();        
        faktura.setIdPouzivatel(3L);
        faktura.setSuma(50);
        faktura.setDatumNakupu(LocalDate.now());
        
        dao.pridajFakturu(faktura);
        String sql = "SELECT * FROM faktura";
        BeanPropertyRowMapper<Faktura> mapper = BeanPropertyRowMapper.newInstance(Faktura.class);
        Faktura f = jdbcTemplate.queryForObject(sql, mapper); 
        
        Assert.assertEquals(new Long(1), f.getId());
        Assert.assertEquals(new Long(3), f.getIdPouzivatel());
        Assert.assertEquals(50, f.getSuma()); 
    }

    /**
     * Test of odstranFakturu method, of class MysqlFakturaDao.
     */
    @Test
    public void testOdstranFakturu() {
        System.out.println("odstranFakturu");
        naplnTestovacieUdaje();
        
        Faktura faktura = new Faktura(); 
        faktura.setId(1L);
        dao.odstranFakturu(faktura);
        String sql1 = "SELECT COUNT(*) FROM faktura"; 
        String sql2 = "SELECT COUNT(*) FROM faktura WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1)); 
        Assert.assertEquals(pocetSVymazanymId, new Long(0));
    }    
}
