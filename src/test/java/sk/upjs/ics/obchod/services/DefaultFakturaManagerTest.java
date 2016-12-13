package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.rowmappers.TovarRowMapper;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultFakturaManagerTest {
    
    private DefaultFakturaManager manager;
    private JdbcTemplate jdbcTemplate;
    private Kosik kosik;
    private Tovar tovar1;
    private Tovar tovar2;
    private Tovar tovar3;
    
    public DefaultFakturaManagerTest() {
        manager = new DefaultFakturaManager(true);
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
        kosik = new Kosik();
    }
    
    @Before
    public void naplnTestovacieUdaje(){
         String sql1 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 2, 1, 80, 'popis1', '@../img/1.JPG', 0), "
                        + "('test2', 1, 1, 40, 'popis2', '@../img/2.JPG', 2),"
                        + "('test3', 3, 2, 23, 'popis3', '@../img/3.JPG', 5)";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "INSERT INTO kategoria (nazov) VALUES('Kategoria1'), ('Kategoria2'), ('Kategoria3')";
        jdbcTemplate.execute(sql2);
        
        String sql3 = "INSERT INTO znacka (nazov) VALUES ('Znacka1'), ('Znacka2')";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";
        
        TovarRowMapper mapper = new TovarRowMapper();
        List<Tovar> tovary= jdbcTemplate.query(sql4, mapper);
        
        tovar1 = tovary.get(0);        
        tovar2 = tovary.get(1);
        tovar3 = tovary.get(2);
        
        kosik.getTovary().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getTovary().put(tovar2, new SimpleIntegerProperty(2));
        kosik.setCelkovaCena(160);
        
        String sql5 = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(1, 100, now()),(2, 55, now())";
        jdbcTemplate.execute(sql5);
        
        String sql6 = "INSERT INTO Tovar_Faktury(id_faktura, nazov_tovaru, nazov_kategorie, "
                + "nazov_znacky, pocet_kusov_tovaru, cena) "
                + "VALUES(1,'test1', 'Kategoria1', 'Znacka1', 4, 320), "
                + "(2, 'test2', 'Kategoria2', 'Znacka2', 6, 240)";
        jdbcTemplate.execute(sql6);
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql);
        kosik = null;
        
        String sql1 = "TRUNCATE TABLE faktura;";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "TRUNCATE TABLE Tovar_Faktury;";
        jdbcTemplate.execute(sql2);
        
        String sql3 = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql4);
    }

    /**
     * Test of vytvorFakturu method, of class DefaultFakturaManager.
     */
    @Test
    public void testVytvorFakturu() {
        System.out.println("vytvorFakturu");
        
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setId(2L);
        pouzivatel.setKosik(kosik);
        
        Long idF = manager.vytvorFakturu(pouzivatel);
        String sql1 = "SELECT COUNT(*) FROM Faktura"; 
        Long pocetF = jdbcTemplate.queryForObject(sql1, Long.class);
        
        String sql2 = "SELECT * FROM faktura WHERE id = 3";
        BeanPropertyRowMapper<Faktura> mapper = BeanPropertyRowMapper.newInstance(Faktura.class);
        Faktura f = jdbcTemplate.queryForObject(sql2, mapper); 
        
        String sql3 = "SELECT COUNT(*) FROM Tovar_Faktury"; 
        Long pocetTF = jdbcTemplate.queryForObject(sql3, Long.class);
        
        String sql4 = "SELECT pocet_kusov_tovaru FROM Tovar_Faktury WHERE nazov_tovaru = 'test1' and id_faktura = 3";
        Long pocet3 = jdbcTemplate.queryForObject(sql4, Long.class);
        
        String sql5 = "SELECT pocet_kusov_tovaru FROM Tovar_Faktury WHERE nazov_tovaru = 'test2' and id_faktura = 3";
        Long pocet4 = jdbcTemplate.queryForObject(sql5, Long.class);
        
        ObservableMap<Tovar, IntegerProperty> tovary = kosik.getTovary();
        
        Assert.assertEquals(new Long(3), pocetF);
        Assert.assertEquals(new Long(3), idF);
        Assert.assertEquals(new Long(3), f.getId());
        Assert.assertEquals(new Long(2), f.getIdPouzivatel());
        Assert.assertEquals(160, f.getSuma());
        Assert.assertEquals(new Long(4), pocetTF);
        Assert.assertEquals(new Long(1), pocet3);
        Assert.assertEquals(new Long(2), pocet4);
        Assert.assertEquals(0, tovary.size());
    }

    /**
     * Test of dajFakturyZaObdobie method, of class DefaultFakturaManager.
     */
    @Test
    public void testDajFakturyZaObdobie() {
     /*   System.out.println("dajFakturyZaObdobie");
        naplnTestovacieUdaje2();
        
        List<Faktura> den = manager.dajFakturyZaObdobie("posledný deň");
        List<Faktura> tyzden = manager.dajFakturyZaObdobie("posledný týždeň");
        List<Faktura> mesiac = manager.dajFakturyZaObdobie("posledný mesiac");
        List<Faktura> rok = manager.dajFakturyZaObdobie("posledný rok");
        List<Faktura> nic = manager.dajFakturyZaObdobie("neni");
        
        Assert.assertEquals(1, den.size());  
        Assert.assertEquals(2, tyzden.size());
        Assert.assertEquals(3, mesiac.size());
        Assert.assertEquals(4, rok.size());
        Assert.assertEquals(null, nic);*/
    }
}
