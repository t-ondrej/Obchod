package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.rowmappers.PouzivatelRowMapper;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class MysqlPouzivatelDaoTest {

    private MysqlPouzivatelDao dao;
    private JdbcTemplate jdbcTemplate;

    public MysqlPouzivatelDaoTest() {
        dao = TestDaoFactory.INSTANCE.getMysqlPouzivatelDao();
        jdbcTemplate = TestDaoFactory.INSTANCE.getJdbcTemplate();
    }

    @Before
    private void naplnTestovacieUdaje() {
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, meno, "
                + "priezvisko, mesto, ulica, psc, hash_hesla, sol, email, "
                + "posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Pouzivatel p1 = new Pouzivatel();

        p1.setPrihlasovacieMeno("test1");
        p1.setMeno("Peter");
        p1.setPriezvisko("Sveter");
        p1.setMesto("Nitra");
        p1.setUlica("Hviezdoslavova 5");
        p1.setPsc(04023);
        p1.setEmail("test1@test.sk");
        p1.setPassword("test1");
        p1.setPoslednePrihlasenie(LocalDateTime.of(2016, 11, 23, 12, 10));
        p1.setJeAdministrator(true);
        Kosik k1 = new Kosik();
        p1.setKosik(k1);

        Pouzivatel p2 = new Pouzivatel();
        p2.setPrihlasovacieMeno("test2");
        p2.setMeno("Ľudomil");
        p2.setPriezvisko("Pohar");
        p2.setMesto("Košice");
        p2.setUlica("Berlinská 8");
        p2.setPsc(04023);
        p2.setEmail("test2@test.sk");
        p2.setPassword("test2");
        p2.setPoslednePrihlasenie(LocalDateTime.of(2016, 10, 13, 12, 10));
        p2.setJeAdministrator(false);
        Kosik k2 = new Kosik();
        p2.setKosik(k2);

        jdbcTemplate.update(sql, p1.getPrihlasovacieMeno(), p1.getMeno(), p1.getPriezvisko(), 
                p1.getMesto(), p1.getUlica(), p1.getPsc(), p1.getPasswordHash(),
                p1.getSol(), p1.getEmail(), p1.getPoslednePrihlasenie(), p1.isJeAdministrator());
        jdbcTemplate.update(sql, p2.getPrihlasovacieMeno(), p2.getMeno(), p2.getPriezvisko(), 
                p2.getMesto(), p2.getUlica(), p2.getPsc(), p2.getPasswordHash(),
                p2.getSol(), p2.getEmail(), p2.getPoslednePrihlasenie(), p2.isJeAdministrator());
    }

    @After
    public void vymazTestovacieUdaje() {
        String sql = "TRUNCATE TABLE pouzivatel;";
        jdbcTemplate.execute(sql);
    }

    /**
     * Test of dajPouzivatelov method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelov() {
        System.out.println("dajPouzivatelov");

        List<Pouzivatel> pouzivatelia = dao.dajPouzivatelov();
        Assert.assertEquals(2, pouzivatelia.size());
    }

    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaMena() {
        System.out.println("dajPouzivatelaPodlaMena");

        Pouzivatel pouzivatel = dao.dajPouzivatela("test1");
        Assert.assertEquals(new Long(1), pouzivatel.getId());
    }

    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaId() {
        System.out.println("dajPouzivatelaPodlaId");

        Pouzivatel p = dao.dajPouzivatela(2L);
        Assert.assertEquals("test2", p.getPrihlasovacieMeno());
        Assert.assertEquals("test2@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());
    }

    /**
     * Test of ulozPouzivatela method, of class MysqlPouzivatelDao. Pridaj
     */
    @Test
    public void testUlozPouzivatelaPridaj() {
        System.out.println("ulozPouzivatela");
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setPrihlasovacieMeno("test");
        pouzivatel.setMeno("Tomáš");
        pouzivatel.setPriezvisko("Jedno");
        pouzivatel.setMesto("Košice");
        pouzivatel.setUlica("Jantárová 8");
        pouzivatel.setPsc(04023);
        pouzivatel.setEmail("test@test.sk");
        pouzivatel.setPassword("test");
        pouzivatel.setPoslednePrihlasenie(LocalDateTime.now());
        pouzivatel.setJeAdministrator(false);
        Kosik k = new Kosik();
        pouzivatel.setKosik(k);

        Long id = dao.ulozPouzivatela(pouzivatel);
        String sql = "SELECT * FROM pouzivatel";
        Pouzivatel p = jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper());

        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals("test", p.getPrihlasovacieMeno());
        Assert.assertEquals("Tomáš", p.getMeno());
        Assert.assertEquals("Jedno", p.getPriezvisko());
        Assert.assertEquals("Košice", p.getMesto());
        Assert.assertEquals("Jantárová 8", p.getUlica());
        Assert.assertEquals(04023, p.getPsc());
        Assert.assertEquals("test@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());
    }

    /**
     * Test of ulozPouzivatela method, of class MysqlPouzivatelDao. Uprav
     */
    @Test
    public void testUlozPouzivatelaUprav() {
        System.out.println("ulozPouzivatela");

        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setId(2L);
        pouzivatel.setPrihlasovacieMeno("test");
        pouzivatel.setMeno("Tomáš");
        pouzivatel.setPriezvisko("Tak");
        pouzivatel.setMesto("Prešov");
        pouzivatel.setUlica("Zimná 8");
        pouzivatel.setPsc(04021);
        pouzivatel.setEmail("test@test.sk");
        pouzivatel.setPassword("test");
        pouzivatel.setPoslednePrihlasenie(LocalDateTime.now());
        pouzivatel.setJeAdministrator(false);
        Kosik k = new Kosik();
        pouzivatel.setKosik(k);

        Long id = dao.ulozPouzivatela(pouzivatel);
        String sql = "SELECT * FROM pouzivatel WHERE id = 2";
        Pouzivatel p = jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper());

        Assert.assertEquals("Tomáš", p.getMeno());
        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), p.getId());
        Assert.assertEquals("Tak", p.getPriezvisko());
        Assert.assertEquals("Prešov", p.getMesto());
        Assert.assertEquals("Zimná 8", p.getUlica());
        Assert.assertEquals(04021, p.getPsc());
        Assert.assertEquals("test", p.getPrihlasovacieMeno());
        Assert.assertEquals("test@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isJeAdministrator());
    }

    /**
     * Test of odstranPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testOdstranPouzivatela() {
        System.out.println("odstranPouzivatela");

        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setId(1L);
        dao.odstranPouzivatela(pouzivatel);
        String sql1 = "SELECT COUNT(*) FROM pouzivatel";
        String sql2 = "SELECT COUNT(*) FROM pouzivatel WHERE id = 1";
        Long pocetOstavajucich = jdbcTemplate.queryForObject(sql1, Long.class);
        Long pocetSVymazanymId = jdbcTemplate.queryForObject(sql2, Long.class);
        Assert.assertEquals(pocetOstavajucich, new Long(1));
        Assert.assertEquals(pocetSVymazanymId, new Long(0));
    }

    /**
     * Test of novePoslednePrihlasenie method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testNovePoslednePrihlasenie() {
        System.out.println("novePoslednePrihlasenie");

        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setId(1L);
        dao.novePoslednePrihlasenie(pouzivatel);
        String sql = "SELECT posledne_prihlasenie FROM pouzivatel WHERE id = 1";
        LocalDate poslednePrihlasenie = jdbcTemplate.queryForObject(sql, LocalDate.class);
        Assert.assertEquals(poslednePrihlasenie, LocalDate.now());
    }
}
