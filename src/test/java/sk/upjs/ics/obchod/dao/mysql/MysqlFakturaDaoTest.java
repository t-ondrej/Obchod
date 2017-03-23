package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlFakturaDaoTest {
    
    private MysqlFakturaDao fakturaDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlFakturaDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        fakturaDao = new MysqlFakturaDao(jdbcTemplate);
        
    }
    
    private void naplnTestovacieUdaje(){
        String sql1 = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES"
                + "(1, 100, date_add(now(), interval -3 minute)),"
                + "(2, 55, date_add(now(), interval -5 minute)), "
                + "(3, 64, date_add(now(), interval -1 year)), "
                + "(3, 60, date_add(now(), interval -1 week)), "
                + "(3, 65, date_add(now(), interval -1 day)), "
                + "(3, 60, date_add(now(), interval -1 month))";
        jdbcTemplate.execute(sql1);
             
        String sql2 = "INSERT INTO Kategoria (nazov) VALUES ('Nezaradene')";
        jdbcTemplate.execute(sql2);
        
        String sql3 = "INSERT INTO Znacka (nazov) VALUES ('Nezaradene')";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 1, 1, 80, 'dobre', '@../img/1.JPG', 2)";
        jdbcTemplate.execute(sql4);
        
        String sql5 = "INSERT INTO Tovar_Faktury(id_faktura, nazov_tovaru, nazov_kategorie, "
                + "nazov_znacky, pocet_kusov_tovaru, cena) VALUES(4, 'test1', 'Nezaradene', 'Nezaradene', 6, 80)";
        jdbcTemplate.execute(sql5);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql1 = "TRUNCATE TABLE faktura;";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "TRUNCATE TABLE Tovar_Faktury;";
        jdbcTemplate.execute(sql2);
        
        String sql3 = "TRUNCATE TABLE Tovar;";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql4);
        
        String sql5 = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql5);
    }
    
    /**
     * Test of dajFaktury method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFaktury() {
        System.out.println("dajFaktury");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = fakturaDao.dajFaktury();        
        Assert.assertEquals(6, faktury.size());       
    }    
    
    /**
     * Test of dajFakturyZaPoslednyDen method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyDen() {
        System.out.println("dajFakturyZaPoslednyDen");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = fakturaDao.dajFakturyZaPoslednyDen();         
        Assert.assertEquals(2, faktury.size()); 
    }

    /**
     * Test of dajFakturyZaPoslednyTyzden method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyTyzden() {
        System.out.println("dajFakturyZaPoslednyTyzden");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = fakturaDao.dajFakturyZaPoslednyTyzden();       
        Assert.assertEquals(4, faktury.size());         
    }

    /**
     * Test of dajFakturyZaPoslednyMesiac method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyMesiac() {
        System.out.println("dajFakturyZaPoslednyMesiac");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = fakturaDao.dajFakturyZaPoslednyMesiac();       
        Assert.assertEquals(5, faktury.size());        
    }

    /**
     * Test of dajFakturyZaPoslednyRok method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyRok() {
        System.out.println("dajFakturyZaPoslednyRok");
        naplnTestovacieUdaje();
        
        List<Faktura> faktury = fakturaDao.dajFakturyZaPoslednyRok();       
        Assert.assertEquals(6, faktury.size());        
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
        faktura.setDatumNakupu(LocalDateTime.now());
        
        Long id = fakturaDao.pridajFakturu(faktura);
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
        fakturaDao.odstranFakturu(faktura);
        String sql1 = "SELECT COUNT(*) FROM faktura"; 
        String sql2 = "SELECT COUNT(*) FROM faktura WHERE id = 1"; 
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(5)); 
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
        
        fakturaDao.pridajTovarFakture(tovar, faktura, pocetTovaru);
        
        String sql2 = "SELECT pocet_kusov_tovaru FROM Tovar_Faktury WHERE nazov_tovaru = 'NazovTovaru' and id_faktura = 5";
        Long pocetT = jdbcTemplate.queryForObject(sql2, Long.class);
        
        String sql3 = "SELECT COUNT(*) FROM Tovar_Faktury";         
        Long pocet = jdbcTemplate.queryForObject(sql3, Long.class);        
        
        Assert.assertEquals(new Long(3), pocetT);        
        Assert.assertEquals(pocet, new Long(2));
    }

    /**
     * Test of dajTovarFaktury method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajTovarFaktury() {
        System.out.println("dajTovarFaktury");
        naplnTestovacieUdaje();
        
        Faktura faktura = new Faktura();
        faktura.setId(4L);
        
        List<Tovar> tovar = fakturaDao.dajTovarFaktury(faktura);

        Assert.assertEquals("test1", tovar.get(0).getNazov()); 
        Assert.assertEquals("Nezaradene", tovar.get(0).getKategoria().getNazov());
        Assert.assertEquals("Nezaradene", tovar.get(0).getZnacka().getNazov());
        Assert.assertEquals("test1", tovar.get(0).getNazov());
        Assert.assertEquals(80, tovar.get(0).getCena()); 
        Assert.assertEquals(6, tovar.get(0).getPocetKusov());        
    }
}
