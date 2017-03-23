package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.rowmappers.TovarRowMapper;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlTovarDaoTest {

    private MysqlTovarDao tovarDao;
    private JdbcTemplate jdbcTemplate;

    private final String vyberTovarSql = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";

    public MysqlTovarDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        tovarDao = new MysqlTovarDao(jdbcTemplate);
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

        List<Tovar> tovar = tovarDao.dajTovar();
        Assert.assertEquals(2, tovar.size());
    }

    /**
     * Test of dajTovarPodlaKategorie method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaKategorie() {
        System.out.println("dajTovarPodlaKategorie");        

        Kategoria kategoria = new Kategoria();
        kategoria.setId(1L);
        kategoria.setNazov("Kategoria1Test");
        Kategoria kategoria3 = new Kategoria();
        kategoria3.setId(3L);
        kategoria3.setNazov("Kategoria3Test");

        List<Tovar> tovar = tovarDao.dajTovarPodlaKategorie(kategoria);
        List<Tovar> tovar3 = tovarDao.dajTovarPodlaKategorie(kategoria3);
        Assert.assertEquals(1, tovar.size());
        Assert.assertEquals(0, tovar3.size());
    }

    /**
     * Test of dajTovarPodlaNazvu method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaNazvu() {
        System.out.println("dajTovarPodlaNazvu");        

        Tovar tovar = tovarDao.dajTovarPodlaNazvu("test1");
        Assert.assertEquals(new Long(1), tovar.getId());
    }

    /**
     * Test of dajTovarPodlaZnacky method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaZnacky() {
        System.out.println("dajTovarPodlaZnacky");        

        Znacka znacka = new Znacka();
        znacka.setId(1L);
        znacka.setNazov("Znacka1Test");
        Znacka znacka3 = new Znacka();
        znacka3.setId(3L);
        znacka3.setNazov("Znacka3Test");

        List<Tovar> tovar = tovarDao.dajTovarPodlaZnacky(znacka);
        List<Tovar> tovar3 = tovarDao.dajTovarPodlaZnacky(znacka3);
        Assert.assertEquals(2, tovar.size());
        Assert.assertEquals(0, tovar3.size());
    }

    /**
     * Test of ulozTovar method, of class MysqlTovarDao. Pridavanie
     */
    @Test
    public void testUlozTovarPridaj() {
        System.out.println("ulozTovar");
        Tovar tovar = new Tovar();

        Kategoria kategoria = new Kategoria();
        kategoria.setId(1L);
        kategoria.setNazov("Nezaradene");
        tovar.setKategoria(kategoria);

        Znacka znacka = new Znacka();
        znacka.setId(1L);
        znacka.setNazov("Nezaradene");
        tovar.setZnacka(znacka);

        tovar.setNazov("nazov1");
        tovar.setCena(42);
        tovar.setObrazokUrl("@../img/3.JPG");
        tovar.setPopis("ok");
        tovar.setPocetKusov(6);

        Long id = tovarDao.ulozTovar(tovar);
        String sql = vyberTovarSql + "WHERE T.id = 3";
        TovarRowMapper mapper = new TovarRowMapper();
        Tovar t = (Tovar) jdbcTemplate.queryForObject(sql, mapper);
        
        Assert.assertEquals(new Long(3), id);
        Assert.assertEquals(new Long(3), t.getId());
        Assert.assertEquals(new Long(1), t.getKategoria().getId());
        Assert.assertEquals(new Long(1), t.getZnacka().getId());
        Assert.assertEquals("nazov1", t.getNazov());
        Assert.assertEquals(42, t.getCena());
        Assert.assertEquals("@../img/3.JPG", t.getObrazokUrl());
        Assert.assertEquals("ok", t.getPopis());
        Assert.assertEquals(6, t.getPocetKusov());
    }

    /**
     * Test of ulozTovar method, of class MysqlTovarDao. Uprav
     */
    @Test
    public void testUlozTovarUprav() {
        System.out.println("ulozTovar");        

        Tovar tovar = new Tovar();
        tovar.setId(2L);

        Kategoria kategoria = new Kategoria();
        kategoria.setId(2L);
        kategoria.setNazov("Nezaradene");
        tovar.setKategoria(kategoria);

        Znacka znacka = new Znacka();
        znacka.setId(1L);
        znacka.setNazov("Nezaradene");
        tovar.setZnacka(znacka);

        tovar.setNazov("nazov1");
        tovar.setCena(42);
        tovar.setObrazokUrl("@../img/3.JPG");
        tovar.setPopis("ok");
        tovar.setPocetKusov(6);

        Long id = tovarDao.ulozTovar(tovar);
        String sql = vyberTovarSql + "WHERE T.id = 2";
        TovarRowMapper mapper = new TovarRowMapper();
        Tovar t = (Tovar) jdbcTemplate.queryForObject(sql, mapper);

        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), t.getId());
        Assert.assertEquals(new Long(2), t.getKategoria().getId());
        Assert.assertEquals(new Long(1), t.getZnacka().getId());
        Assert.assertEquals("nazov1", t.getNazov());
        Assert.assertEquals(42, t.getCena());
        Assert.assertEquals("@../img/3.JPG", t.getObrazokUrl());
        Assert.assertEquals("ok", t.getPopis());
        Assert.assertEquals(6, t.getPocetKusov());
    }

    /**
     * Test of odstranTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");        
        Tovar tovar = new Tovar();
        tovar.setId(1L);

        tovarDao.odstranTovar(tovar);
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

        Tovar t = tovarDao.najdiPodlaId(1L);
        Assert.assertEquals(new Long(2), t.getKategoria().getId());
        Assert.assertEquals(new Long(1), t.getZnacka().getId());
        Assert.assertEquals("test1", t.getNazov());
        Assert.assertEquals(80, t.getCena());
        Assert.assertEquals("@../img/1.JPG", t.getObrazokUrl());
        Assert.assertEquals("dobre", t.getPopis());
        Assert.assertEquals(2, t.getPocetKusov());
    }

    /**
     * Test of nastavTovaruPocetKusov method, of class MysqlTovarDao.
     */
    @Test
    public void testNastavTovaruPocetKusov() {
        System.out.println("nastavTovaruPocetKusov");        
        Tovar tovar = new Tovar();
        tovar.setId(1L);

        tovarDao.nastavTovaruPocetKusov(tovar, 9);
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
        Tovar tovar = new Tovar();
        tovar.setId(1L);

        int pocet = tovarDao.dajPocetTovaru(tovar);
        Assert.assertEquals(2, pocet);
    }
}
