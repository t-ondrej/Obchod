package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public class MysqlBillDaoTest {
    
    private MysqlBillDao fakturaDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlBillDaoTest(){
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        fakturaDao = new MysqlBillDao(jdbcTemplate);
        
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
        
        List<Bill> faktury = fakturaDao.getAll();        
        Assert.assertEquals(6, faktury.size());       
    }    
    
    /**
     * Test of dajFakturyZaPoslednyDen method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyDen() {
        System.out.println("dajFakturyZaPoslednyDen");
        naplnTestovacieUdaje();
        
        List<Bill> faktury = fakturaDao.getBillsForLastDay();         
        Assert.assertEquals(2, faktury.size()); 
    }

    /**
     * Test of dajFakturyZaPoslednyTyzden method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyTyzden() {
        System.out.println("dajFakturyZaPoslednyTyzden");
        naplnTestovacieUdaje();
        
        List<Bill> faktury = fakturaDao.getBillsForLastWeek();       
        Assert.assertEquals(4, faktury.size());         
    }

    /**
     * Test of dajFakturyZaPoslednyMesiac method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyMesiac() {
        System.out.println("dajFakturyZaPoslednyMesiac");
        naplnTestovacieUdaje();
        
        List<Bill> faktury = fakturaDao.getBillsForLastMonth();       
        Assert.assertEquals(5, faktury.size());        
    }

    /**
     * Test of dajFakturyZaPoslednyRok method, of class MysqlFakturaDao.
     */
    @Test
    public void testDajFakturyZaPoslednyRok() {
        System.out.println("dajFakturyZaPoslednyRok");
        naplnTestovacieUdaje();
        
        List<Bill> faktury = fakturaDao.getBillsForLastYear();       
        Assert.assertEquals(6, faktury.size());        
    }


    /**
     * Test of pridajFakturu method, of class MysqlFakturaDao.
     */
    @Test
    public void testPridajFakturu() {
        System.out.println("pridajFakturu");        
        Bill faktura = new Bill();        
        faktura.setUserId(3L);
        faktura.setTotalPrice(50);
        faktura.setPurchaseDate(LocalDateTime.now());
        
        Long id = fakturaDao.saveOrUpdate(faktura);
        String sql = "SELECT * FROM faktura";
        BeanPropertyRowMapper<Bill> mapper = BeanPropertyRowMapper.newInstance(Bill.class);
        Bill f = jdbcTemplate.queryForObject(sql, mapper); 
        
        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals(new Long(1), f.getId());
        Assert.assertEquals(new Long(3), f.getUserId());
        Assert.assertEquals(50, f.getTotalPrice()); 
    }

    /**
     * Test of odstranFakturu method, of class MysqlFakturaDao.
     */
    @Test
    public void testOdstranFakturu() {
        System.out.println("odstranFakturu");
        naplnTestovacieUdaje();
        
        Bill faktura = new Bill(); 
        faktura.setId(1L);
        fakturaDao.delete(faktura);
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
        
        Product tovar = new Product(); 
        tovar.setId(2L);
        tovar.setName("NazovTovaru");
        tovar.setPrice(150);
        
        Category kategoria = new Category();
        kategoria.setName("NazovKategorie");
        tovar.setCategory(kategoria);
        
        Brand znacka = new Brand();
        znacka.setName("NazovZnacky");
        tovar.setBrand(znacka);
        
        Bill faktura = new Bill();
        faktura.setId(5L);
        
        int pocetTovaru = 3;  
        
        String sql1 = "SELECT COUNT(*) FROM Tovar_Faktury"; 
        Long pocetPred = jdbcTemplate.queryForObject(sql1, Long.class);
        
        fakturaDao.addProductToBill(tovar, faktura, pocetTovaru);
        
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
        
        Bill faktura = new Bill();
        faktura.setId(4L);
        
        List<Product> tovar = fakturaDao.getBillProducts(faktura);

        Assert.assertEquals("test1", tovar.get(0).getName()); 
        Assert.assertEquals("Nezaradene", tovar.get(0).getCategory().getName());
        Assert.assertEquals("Nezaradene", tovar.get(0).getBrand().getName());
        Assert.assertEquals("test1", tovar.get(0).getName());
        Assert.assertEquals(80, tovar.get(0).getPrice()); 
        Assert.assertEquals(6, tovar.get(0).getQuantity());        
    }
}
