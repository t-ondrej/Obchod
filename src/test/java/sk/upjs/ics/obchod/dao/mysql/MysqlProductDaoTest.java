package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.rowmappers.ProductRowMapper;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public class MysqlProductDaoTest {

    private MysqlProductDao tovarDao;
    private JdbcTemplate jdbcTemplate;

    private final String vyberTovarSql = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";

    public MysqlProductDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        tovarDao = new MysqlProductDao(jdbcTemplate);
    }

    @Before
    public void naplnTestovacieUdaje() {
        String sql = "INSERT INTO tovar (nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov)"
                + " VALUES ('test1', 2, 1, 80, 'dobre', '@../img/1.JPG', 2), "
                + "('test2', 1, 1, 40, 'skvele', '@../img/2.JPG', 0)";
        jdbcTemplate.execute(sql);

        String sql2 = "INSERT INTO kategoria (nazov) VALUES ('kategoria1'), ('kategoria2')";
        jdbcTemplate.execute(sql2);

        String sql3 = "INSERT INTO znacka (nazov) VALUES ('znacka1')";
        jdbcTemplate.execute(sql3);
    }

    @After
    public void vymazTestovacieUdaje() {
        String sql = "TRUNCATE TABLE tovar;";
        jdbcTemplate.execute(sql);
        String sql1 = "TRUNCATE TABLE kategoria;";
        jdbcTemplate.execute(sql1);
        String sql2 = "TRUNCATE TABLE znacka;";
        jdbcTemplate.execute(sql2);
    }

    /**
     * Test of dajTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovar() {
        System.out.println("dajTovar");        

        List<Product> tovar = tovarDao.getAll();
        Assert.assertEquals(2, tovar.size());
    }

    /**
     * Test of dajTovarPodlaKategorie method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaKategorie() {
        System.out.println("dajTovarPodlaKategorie");        

        Category kategoria = new Category();
        kategoria.setId(1L);
        kategoria.setName("Kategoria1Test");
        Category kategoria3 = new Category();
        kategoria3.setId(3L);
        kategoria3.setName("Kategoria3Test");

        List<Product> tovar = tovarDao.findProductsByCategory(kategoria);
        List<Product> tovar3 = tovarDao.findProductsByCategory(kategoria3);
        Assert.assertEquals(1, tovar.size());
        Assert.assertEquals(0, tovar3.size());
    }

    /**
     * Test of dajTovarPodlaNazvu method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaNazvu() {
        System.out.println("dajTovarPodlaNazvu");        

        Product tovar = tovarDao.findProductsByName("test1");
        Assert.assertEquals(new Long(1), tovar.getId());
    }

    /**
     * Test of dajTovarPodlaZnacky method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaZnacky() {
        System.out.println("dajTovarPodlaZnacky");        

        Brand znacka = new Brand();
        znacka.setId(1L);
        znacka.setName("Znacka1Test");
        Brand znacka3 = new Brand();
        znacka3.setId(3L);
        znacka3.setName("Znacka3Test");

        List<Product> tovar = tovarDao.findProductsByBrand(znacka);
        List<Product> tovar3 = tovarDao.findProductsByBrand(znacka3);
        Assert.assertEquals(2, tovar.size());
        Assert.assertEquals(0, tovar3.size());
    }

    /**
     * Test of ulozTovar method, of class MysqlTovarDao. Pridavanie
     */
    @Test
    public void testUlozTovarPridaj() {
        System.out.println("ulozTovar");
        Product tovar = new Product();

        Category kategoria = new Category();
        kategoria.setId(1L);
        kategoria.setName("Nezaradene");
        tovar.setCategory(kategoria);

        Brand znacka = new Brand();
        znacka.setId(1L);
        znacka.setName("Nezaradene");
        tovar.setBrand(znacka);

        tovar.setName("nazov1");
        tovar.setPrice(42);
        tovar.setImagePath("@../img/3.JPG");
        tovar.setDescription("ok");
        tovar.setQuantity(6);

        Long id = tovarDao.saveOrUpdate(tovar);
        String sql = vyberTovarSql + "WHERE T.id = 3";
        ProductRowMapper mapper = new ProductRowMapper();
        Product t = (Product) jdbcTemplate.queryForObject(sql, mapper);
        
        Assert.assertEquals(new Long(3), id);
        Assert.assertEquals(new Long(3), t.getId());
        Assert.assertEquals(new Long(1), t.getCategory().getId());
        Assert.assertEquals(new Long(1), t.getBrand().getId());
        Assert.assertEquals("nazov1", t.getName());
        Assert.assertEquals(42, t.getPrice());
        Assert.assertEquals("@../img/3.JPG", t.getImagePath());
        Assert.assertEquals("ok", t.getDescription());
        Assert.assertEquals(6, t.getQuantity());
    }

    /**
     * Test of ulozTovar method, of class MysqlTovarDao. Uprav
     */
    @Test
    public void testUlozTovarUprav() {
        System.out.println("ulozTovar");        

        Product tovar = new Product();
        tovar.setId(2L);

        Category kategoria = new Category();
        kategoria.setId(2L);
        kategoria.setName("Nezaradene");
        tovar.setCategory(kategoria);

        Brand znacka = new Brand();
        znacka.setId(1L);
        znacka.setName("Nezaradene");
        tovar.setBrand(znacka);

        tovar.setName("nazov1");
        tovar.setPrice(42);
        tovar.setImagePath("@../img/3.JPG");
        tovar.setDescription("ok");
        tovar.setQuantity(6);

        Long id = tovarDao.saveOrUpdate(tovar);
        String sql = vyberTovarSql + "WHERE T.id = 2";
        ProductRowMapper mapper = new ProductRowMapper();
        Product t = (Product) jdbcTemplate.queryForObject(sql, mapper);

        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), t.getId());
        Assert.assertEquals(new Long(2), t.getCategory().getId());
        Assert.assertEquals(new Long(1), t.getBrand().getId());
        Assert.assertEquals("nazov1", t.getName());
        Assert.assertEquals(42, t.getPrice());
        Assert.assertEquals("@../img/3.JPG", t.getImagePath());
        Assert.assertEquals("ok", t.getDescription());
        Assert.assertEquals(6, t.getQuantity());
    }

    /**
     * Test of odstranTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");        
        Product tovar = new Product();
        tovar.setId(1L);

        tovarDao.delete(tovar);
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

        Product t = tovarDao.findById(1L);
        Assert.assertEquals(new Long(2), t.getCategory().getId());
        Assert.assertEquals(new Long(1), t.getBrand().getId());
        Assert.assertEquals("test1", t.getName());
        Assert.assertEquals(80, t.getPrice());
        Assert.assertEquals("@../img/1.JPG", t.getImagePath());
        Assert.assertEquals("dobre", t.getDescription());
        Assert.assertEquals(2, t.getQuantity());
    }

    /**
     * Test of nastavTovaruPocetKusov method, of class MysqlTovarDao.
     */
    @Test
    public void testNastavTovaruPocetKusov() {
        System.out.println("nastavTovaruPocetKusov");        
        Product tovar = new Product();
        tovar.setId(1L);

        tovarDao.setProductQuantity(tovar, 9);
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
        Product tovar = new Product();
        tovar.setId(1L);

        int pocet = tovarDao.getProductQuantity(tovar);
        Assert.assertEquals(2, pocet);
    }
}
