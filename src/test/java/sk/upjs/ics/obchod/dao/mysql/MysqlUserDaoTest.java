package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.rowmappers.UserRowMapper;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;

public class MysqlUserDaoTest {

    private MysqlUserDao pouzivatelDao;
    private JdbcTemplate jdbcTemplate;

    public MysqlUserDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        pouzivatelDao = new MysqlUserDao(jdbcTemplate);
    }

    private void naplnTestovacieUdaje() {
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, meno, "
                + "priezvisko, mesto, ulica, psc, hash_hesla, sol, email, "
                + "posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        User p1 = new User();

        p1.setLogin("test1");
        p1.setName("Peter");
        p1.setSurname("Sveter");
        p1.setCity("Nitra");
        p1.setStreet("Hviezdoslavova 5");
        p1.setPostalCode(04023);
        p1.setEmail("test1@test.sk");
        p1.setPassword("test1");
        p1.setLastLogin(LocalDateTime.of(2016, 11, 23, 12, 10));
        p1.setIsAdmin(true);
        Cart k1 = new Cart();
        p1.setCart(k1);

        User p2 = new User();
        p2.setLogin("test2");
        p2.setName("Ľudomil");
        p2.setSurname("Pohar");
        p2.setCity("Košice");
        p2.setStreet("Berlinská 8");
        p2.setPostalCode(04023);
        p2.setEmail("test2@test.sk");
        p2.setPassword("test2");
        p2.setLastLogin(LocalDateTime.of(2016, 10, 13, 12, 10));
        p2.setIsAdmin(false);
        Cart k2 = new Cart();
        p2.setCart(k2);

        jdbcTemplate.update(sql, p1.getLogin(), p1.getName(), p1.getSurname(), 
                p1.getCity(), p1.getStreet(), p1.getPostalCode(), p1.getPasswordHash(),
                p1.getSalt(), p1.getEmail(), p1.getLastLogin(), p1.isAdministrator());
        jdbcTemplate.update(sql, p2.getLogin(), p2.getName(), p2.getSurname(), 
                p2.getCity(), p2.getStreet(), p2.getPostalCode(), p2.getPasswordHash(),
                p2.getSalt(), p2.getEmail(), p2.getLastLogin(), p2.isAdministrator());
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
        naplnTestovacieUdaje();

        List<User> pouzivatelia = pouzivatelDao.getAll();
        Assert.assertEquals(2, pouzivatelia.size());
    }

    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaMena() {
        System.out.println("dajPouzivatelaPodlaMena");
        naplnTestovacieUdaje();

        User pouzivatel = pouzivatelDao.findByName("test1");
        Assert.assertEquals(new Long(1), pouzivatel.getId());
    }

    /**
     * Test of dajPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testDajPouzivatelaPodlaId() {
        System.out.println("dajPouzivatelaPodlaId");
        naplnTestovacieUdaje();

        User p = pouzivatelDao.findById(2L);
        Assert.assertEquals("test2", p.getLogin());
        Assert.assertEquals("test2@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isAdministrator());
    }

    /**
     * Test of ulozPouzivatela method, of class MysqlPouzivatelDao. Pridaj
     */
    @Test
    public void testUlozPouzivatelaPridaj() {
        System.out.println("ulozPouzivatela");
        User pouzivatel = new User();
        pouzivatel.setLogin("test");
        pouzivatel.setName("Tomáš");
        pouzivatel.setSurname("Jedno");
        pouzivatel.setCity("Košice");
        pouzivatel.setStreet("Jantárová 8");
        pouzivatel.setPostalCode(04023);
        pouzivatel.setEmail("test@test.sk");
        pouzivatel.setPassword("test");
        pouzivatel.setLastLogin(LocalDateTime.now());
        pouzivatel.setIsAdmin(false);
        Cart k = new Cart();
        pouzivatel.setCart(k);

        Long id = pouzivatelDao.saveOrUpdate(pouzivatel);
        String sql = "SELECT * FROM pouzivatel";
        User p = jdbcTemplate.queryForObject(sql, new UserRowMapper());

        Assert.assertEquals(new Long(1), id);
        Assert.assertEquals("test", p.getLogin());
        Assert.assertEquals("Tomáš", p.getName());
        Assert.assertEquals("Jedno", p.getSurname());
        Assert.assertEquals("Košice", p.getCity());
        Assert.assertEquals("Jantárová 8", p.getStreet());
        Assert.assertEquals(04023, p.getPostalCode());
        Assert.assertEquals("test@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isAdministrator());
    }

    /**
     * Test of ulozPouzivatela method, of class MysqlPouzivatelDao. Uprav
     */
    @Test
    public void testUlozPouzivatelaUprav() {
        System.out.println("ulozPouzivatela");
        naplnTestovacieUdaje();

        User pouzivatel = new User();
        pouzivatel.setId(2L);
        pouzivatel.setLogin("test");
        pouzivatel.setName("Tomáš");
        pouzivatel.setSurname("Tak");
        pouzivatel.setCity("Prešov");
        pouzivatel.setStreet("Zimná 8");
        pouzivatel.setPostalCode(04021);
        pouzivatel.setEmail("test@test.sk");
        pouzivatel.setPassword("test");
        pouzivatel.setLastLogin(LocalDateTime.now());
        pouzivatel.setIsAdmin(false);
        Cart k = new Cart();
        pouzivatel.setCart(k);

        Long id = pouzivatelDao.saveOrUpdate(pouzivatel);
        String sql = "SELECT * FROM pouzivatel WHERE id = 2";
        User p = jdbcTemplate.queryForObject(sql, new UserRowMapper());

        Assert.assertEquals("Tomáš", p.getName());
        Assert.assertEquals(new Long(2), id);
        Assert.assertEquals(new Long(2), p.getId());
        Assert.assertEquals("Tak", p.getSurname());
        Assert.assertEquals("Prešov", p.getCity());
        Assert.assertEquals("Zimná 8", p.getStreet());
        Assert.assertEquals(04021, p.getPostalCode());
        Assert.assertEquals("test", p.getLogin());
        Assert.assertEquals("test@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isAdministrator());
    }

    /**
     * Test of odstranPouzivatela method, of class MysqlPouzivatelDao.
     */
    @Test
    public void testOdstranPouzivatela() {
        System.out.println("odstranPouzivatela");
        naplnTestovacieUdaje();

        User pouzivatel = new User();
        pouzivatel.setId(1L);
        pouzivatelDao.delete(pouzivatel);
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
        naplnTestovacieUdaje();

        User pouzivatel = new User();
        pouzivatel.setId(1L);
        pouzivatelDao.updateLastLogin(pouzivatel);
        String sql = "SELECT posledne_prihlasenie FROM pouzivatel WHERE id = 1";
        LocalDate poslednePrihlasenie = jdbcTemplate.queryForObject(sql, LocalDate.class);
        Assert.assertEquals(poslednePrihlasenie, LocalDate.now());
    }
}
