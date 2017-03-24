package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlUserDao;
import sk.upjs.ics.obchod.dao.rowmappers.UserRowMapper;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.dao.IUserDao;

public class UserManagerTest {

    private IUserManager pouzivatelManager;
    private JdbcTemplate jdbcTemplate;

    public UserManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
       
        IUserDao pouzivatelDao = new MysqlUserDao(jdbcTemplate);
        pouzivatelManager = new UserManager(pouzivatelDao);
    }

    private void naplnTestovacieUdaje() {
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?)";

        User p1 = new User();

        p1.setLogin("test1");
        p1.setEmail("test1@test.sk");
        p1.setPassword("test1");
        p1.setLastLogin(LocalDateTime.of(2016, 11, 3, 12, 10));
        p1.setIsAdmin(true);
        Cart k1 = new Cart();
        p1.setCart(k1);

        User p2 = new User();
        p2.setLogin("test2");
        p2.setEmail("test2@test.sk");
        p2.setPassword("test2");
        p2.setLastLogin(LocalDateTime.of(2016, 10, 1, 12, 10));
        p2.setIsAdmin(false);
        Cart k2 = new Cart();
        p2.setCart(k2);

        jdbcTemplate.update(sql, p1.getLogin(), p1.getPasswordHash(),
                p1.getSalt(), p1.getEmail(), p1.getLastLogin(), p1.isAdministrator());
        jdbcTemplate.update(sql, p2.getLogin(), p2.getPasswordHash(),
                p2.getSalt(), p2.getEmail(), p2.getLastLogin(), p2.isAdministrator());
    }

    @After
    public void vymazTestovacieUdaje() {
        String sql = "TRUNCATE TABLE pouzivatel;";
        jdbcTemplate.execute(sql);
    }

    /**
     * Test of prihlasPouzivatela method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testPrihlasPouzivatela() {
        System.out.println("prihlasPouzivatela");
        naplnTestovacieUdaje();

        boolean podariloSa = pouzivatelManager.signInUser("test1", "test1");

        Assert.assertEquals(true, podariloSa);
        Assert.assertEquals(new Long(1), pouzivatelManager.getSignedInUser().getId());
        Assert.assertEquals(true, pouzivatelManager.isSignedIn().getValue());
    }

    /**
     * Test of registrujPouzivatela method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testRegistrujPouzivatela() {
        System.out.println("registrujPouzivatela");
        naplnTestovacieUdaje();

        pouzivatelManager.signUpUser("test3", "test3", "test3@test.sk", "Tomas", "Jedno", "Veľké Kapušany", "Bratislavská", 05502);
        String sql = "SELECT * FROM pouzivatel WHERE prihlasovacie_meno = 'test3'";
        User p = jdbcTemplate.queryForObject(sql, new UserRowMapper());

        Assert.assertEquals(new Long(3), p.getId());
        Assert.assertEquals("test3", p.getLogin());
        Assert.assertEquals("test3@test.sk", p.getEmail());
        Assert.assertEquals(false, p.isAdministrator());
    }

    /**
     * Test of jeVolneMeno method, of class DefaultPouzivatelManager.
     */
    @Test
    public void testJeVolneMeno() {
        System.out.println("jeVolneMeno");
        naplnTestovacieUdaje();

        boolean volne = pouzivatelManager.isUsernameAvailable("testtest");
        boolean obsadene = pouzivatelManager.isUsernameAvailable("test1");
        Assert.assertEquals(true, volne);
        Assert.assertEquals(false, obsadene);
    }
}
