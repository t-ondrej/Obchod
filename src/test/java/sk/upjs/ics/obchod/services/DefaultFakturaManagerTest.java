package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
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
    
    private void naplnTestovacieUdaje(){
        String sql1 = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 2, 1, 80, 'dobre', '@../img/1.JPG', 0), "
                        + "('test2', 1, 1, 40, 'skvele', '@../img/2.JPG', 2),"
                        + "('test3', 3, 2, 23, 'ok', '@../img/3.JPG', 5)";
        jdbcTemplate.execute(sql1);
        
        String sql2 = "SELECT * FROM tovar";
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        List<Tovar> tovary= jdbcTemplate.query(sql2, mapper);
        
        tovar1 = tovary.get(0);        
        tovar2 = tovary.get(1);
        tovar3 = tovary.get(2);
        
        kosik.getTovary().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getTovary().put(tovar2, new SimpleIntegerProperty(2));
        kosik.setCelkovaCena(160);
        
        String sql3 = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(1, 100, now()),(2, 55, now())";
        jdbcTemplate.execute(sql3);
        
        String sql4 = "INSERT INTO Tovar_Faktury(id_tovar, id_faktura, pocet_kusov) VALUES(2,1,4), (3,2,6)";
        jdbcTemplate.execute(sql4);
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
    }

    /**
     * Test of vytvorFakturu method, of class DefaultFakturaManager.
     */
    @Test
    public void testVytvorFakturu() {
        System.out.println("vytvorFakturu");
        naplnTestovacieUdaje();
        
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
        String sql4 = "SELECT pocet_kusov FROM Tovar_Faktury WHERE id_tovar = 1 and id_faktura = 3";
        Long pocet3 = jdbcTemplate.queryForObject(sql4, Long.class);
        String sql5 = "SELECT pocet_kusov FROM Tovar_Faktury WHERE id_tovar = 2 and id_faktura = 3";
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
    
}
