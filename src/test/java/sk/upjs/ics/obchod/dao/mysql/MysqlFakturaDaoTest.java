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
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlFakturaDaoTest {
    
    private MysqlFakturaDao dao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlFakturaDaoTest(){
        dao = TestDaoFactory.INSTANCE.getMysqlFakturaDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }
    
    private void naplnTestovacieUdaje(){
        String sql1 = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(1, 100, now()),(2, 55, now())";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "INSERT INTO Tovar_Faktury(id_faktura, nazov_tovaru, nazov_kategorie, nazov_znacky, pocet_kusov_tovaru, cena) VALUES(4, 'Test', 'Nezaradene', 'Nezaradene', 6, 90)";
        jdbcTemplate.execute(sql2);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql1 = "TRUNCATE TABLE faktura;";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "TRUNCATE TABLE Tovar_Faktury;";
        jdbcTemplate.execute(sql2);
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
        
        Long id = dao.pridajFakturu(faktura);
        String sql = "SELECT * FROM faktura";
        BeanPropertyRowMapper<Faktura> mapper = BeanPropertyRowMapper.newInstance(Faktura.class);
        Faktura f = jdbcTemplate.queryForObject(sql, mapper); 
        
        Assert.assertEquals(new Long(1), id);
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

    /**
     * Test of pridajTovarFakture method, of class MysqlFakturaDao.
     */
    @Test
    public void testPridajTovarFakture() {
        System.out.println("pridajTovarFakture");
        naplnTestovacieUdaje();
        
        Tovar tovar = new Tovar(); 
        tovar.setId(2L);
        tovar.setNazov("NazovTovaru");
        tovar.setCena(150);
        
        Kategoria kategoria = new Kategoria();
        kategoria.setNazov("NazovKategorie");
        tovar.setKategoria(kategoria);
        
        Znacka znacka = new Znacka();
        znacka.setNazov("NazovZnacky");
        tovar.setZnacka(znacka);
        
        Faktura faktura = new Faktura();
        faktura.setId(5L);
        
        int pocetTovaru = 3;  
        
        String sql1 = "SELECT COUNT(*) FROM Tovar_Faktury"; 
        Long pocetPred = jdbcTemplate.queryForObject(sql1, Long.class);
        
        dao.pridajTovarFakture(tovar, faktura, pocetTovaru);
        
        String sql2 = "SELECT pocet_kusov_tovaru FROM Tovar_Faktury WHERE nazov_tovaru = 'NazovTovaru' and id_faktura = 5";
        Long pocetT = jdbcTemplate.queryForObject(sql2, Long.class);
        
        String sql3 = "SELECT COUNT(*) FROM Tovar_Faktury";         
        Long pocetPo = jdbcTemplate.queryForObject(sql3, Long.class);        
        
        Assert.assertEquals(new Long(3), pocetT);
        Assert.assertEquals(pocetPred, new Long(1)); 
        Assert.assertEquals(pocetPo, new Long(2));
    }
}
