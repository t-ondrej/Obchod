package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;


public class DefaultKosikManagerTest {

    private KosikManager manazer;
    private JdbcTemplate jdbcTemplate;    
    private Kosik kosik;
    private Tovar tovar1;
    private Tovar tovar2;
    private Tovar tovar3;
    
    public DefaultKosikManagerTest(){
        manazer = new DefaultKosikManager(true);
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();        
        kosik = new Kosik();
    }
    
    private void naplnTestovacieUdaje(){
        String sql = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " values ('test1', 2, 1, 80, 'dobre', '@../img/1.JPG', 0), "
                        + "('test2', 1, 1, 40, 'skvele', '@../img/2.JPG', 2),"
                        + "('test3', 3, 2, 23, 'ok', '@../img/3.JPG', 5)";
        jdbcTemplate.execute(sql);
        
        String sql2 = "SELECT * FROM tovar";
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        List<Tovar> tovary= jdbcTemplate.query(sql2, mapper);
        
        tovar1 = tovary.get(0);        
        tovar2 = tovary.get(1);
        tovar3 = tovary.get(2);
        
        kosik.getTovary().put(tovar1, new SimpleIntegerProperty(1));
        kosik.getTovary().put(tovar2, new SimpleIntegerProperty(2));
    }
    
    @After 
    public void vymazTestovacieUdaje(){
        String sql = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql);
        kosik = null;
    }
   
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar ma nulovy pocet kusov
     */
    @Test
    public void testPridajTovarDoKosikaNepridaSa() {
        System.out.println("pridajTovarDoKosikaNepridaSa");
        naplnTestovacieUdaje();
                
        boolean pridaloSa = manazer.pridajTovarDoKosika(tovar1, kosik);
        int pocetPo = kosik.getTovary().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaru = jdbcTemplate.queryForObject(sql, Long.class);
                
        Assert.assertTrue(!pridaloSa);
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(0), pocetKusovTovaru);        
    }
    
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar ma nenulovy pocet kusov a je uz v kosiku
     */
    @Test
    public void testPridajTovarDoKosikaPridaSaJeTamUz() {
        System.out.println("pridajTovarDoKosikaPridaSaJeTamUz");
        naplnTestovacieUdaje();
                
        boolean pridaloSa = manazer.pridajTovarDoKosika(tovar2, kosik);
        int pocetPo = kosik.getTovary().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getTovary().get(tovar2);
                
        Assert.assertTrue(pridaloSa);
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaruPo); 
        Assert.assertEquals(new SimpleIntegerProperty(3).getValue(), pocetVkosikuPo.getValue());
    }
    
    /**
     * Test of pridajTovarDoKosika method, of class DefaultKosikManager.
     * Tovar ma nenulovy pocet kusov a je uz v kosiku
     */
    @Test
    public void testPridajTovarDoKosikaPridaSaNieJeTam() {
        System.out.println("pridajTovarDoKosikaPridaSaNieJeTam");
        naplnTestovacieUdaje();
                
        boolean pridaloSa = manazer.pridajTovarDoKosika(tovar3, kosik);
        int pocetPo = kosik.getTovary().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 3;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getTovary().get(tovar3);
                
        Assert.assertTrue(pridaloSa);
        Assert.assertEquals(3, pocetPo);
        Assert.assertEquals(new Long(4), pocetKusovTovaruPo); 
        Assert.assertEquals(new SimpleIntegerProperty(1).getValue(), pocetVkosikuPo.getValue());
    }  
    
     /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     * Tovar, ktory ma v kosiku jeden kus
     */    
    @Test
    public void testOdoberTovarZKosikaMaJedenKus() {
        System.out.println("odoberTovarZKosikaMaJedenKus");
        naplnTestovacieUdaje();
                
        manazer.odoberTovarZKosika(tovar1, kosik);
        int pocetPo = kosik.getTovary().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);                       
        
        Assert.assertEquals(1, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaruPo);         
    }
    
    /**
     * Test of odoberTovarZKosika method, of class DefaultKosikManager.
     * Tovar, ktory ma v kosiku viac kusov
     */    
    @Test
    public void testOdoberTovarZKosikaMaViacKusov() {
        System.out.println("odoberTovarZKosikaMaViacKusov");
        naplnTestovacieUdaje();
                
        manazer.odoberTovarZKosika(tovar2, kosik);
        int pocetPo = kosik.getTovary().size();  
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaruPo = jdbcTemplate.queryForObject(sql, Long.class);
        IntegerProperty pocetVkosikuPo = kosik.getTovary().get(tovar2);
        
        Assert.assertEquals(2, pocetPo);
        Assert.assertEquals(new Long(3), pocetKusovTovaruPo);   
        Assert.assertEquals(new SimpleIntegerProperty(1).getValue(), pocetVkosikuPo.getValue());
    }
    
    /**
     * Test of dajTovaryKosika method, of class DefaultKosikManager.
     */
    @Test
    public void testDajTovaryKosika() {
        System.out.println("dajTovaryKosika");
        naplnTestovacieUdaje();
        
        List<Tovar> tovary = manazer.dajTovaryKosika(kosik);
        int pocet = tovary.size();        
        
        Assert.assertEquals(2, pocet);        
    }

    /**
     * Test of dajPocetTovaruVKosiku method, of class DefaultKosikManager.
     */
    @Test
    public void testDajPocetTovaruVKosiku() {
        System.out.println("dajPocetTovaruVKosiku");
        naplnTestovacieUdaje();        
        
        int pocet = manazer.dajPocetTovaruVKosiku(tovar1, kosik);       
        Assert.assertEquals(1, pocet);
    }

    /**
     * Test of pocetTovaruVKosikuProperty method, of class DefaultKosikManager.
     */
    @Test
    public void testPocetTovaruVKosikuProperty() {
        System.out.println("pocetTovaruVKosikuProperty");       
        naplnTestovacieUdaje();        
        
        IntegerProperty  pocet = manazer.pocetTovaruVKosikuProperty(tovar1, kosik);      
        Assert.assertEquals(1, pocet.getValue().intValue());
    }

    /**
     * Test of tovarKosikaObservableList method, of class DefaultKosikManager.
     */
    @Test
    public void testTovarKosikaObservableList() {
        System.out.println("tovarKosikaObservableList");
        naplnTestovacieUdaje();
        
        ObservableList<Tovar> tovary = manazer.tovarKosikaObservableList(kosik);
        int pocet = tovary.size();        
        
        Assert.assertEquals(2, pocet); 
    }

    /**
     * Test of vyprazdniKosik method, of class DefaultKosikManager.
     */
    @Test
    public void testVyprazdniKosik() {
        System.out.println("vyprazdniKosik");       
        naplnTestovacieUdaje();
        
        manazer.vyprazdniKosik(kosik);
        
        int pocetPo = kosik.getTovary().size();  
        String sql1 = "SELECT pocet_kusov FROM tovar WHERE id = 1;";
        Long pocetKusovTovaru1 = jdbcTemplate.queryForObject(sql1, Long.class);
        String sql2 = "SELECT pocet_kusov FROM tovar WHERE id = 2;";
        Long pocetKusovTovaru2 = jdbcTemplate.queryForObject(sql2, Long.class);                      
        
        Assert.assertEquals(0, pocetPo);
        Assert.assertEquals(new Long(1), pocetKusovTovaru1);  
        Assert.assertEquals(new Long(4), pocetKusovTovaru2);
    }
}
