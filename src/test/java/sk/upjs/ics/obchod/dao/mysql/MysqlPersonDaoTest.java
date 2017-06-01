package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class MysqlPersonDaoTest {

    private MysqlPersonDao userDao;
    private JdbcTemplate jdbcTemplate;

    public MysqlPersonDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        userDao = new MysqlPersonDao(jdbcTemplate);
    }
    
    @BeforeClass
    public static void setUp() {
        TestDataProvider.insertTestData();
    }
    
    @AfterClass
    public static void tearDown() {
        TestDataProvider.clearTestData();
    }
    
    @Test
    public void testDajPouzivatelov() {
        System.out.println("dajPouzivatelov");

        List<Person> persons = userDao.getAll();
        Assert.assertNotNull(persons);
        persons.forEach(p -> Assert.assertNotNull(p.getId()));
    }

    @Test
    public void testDajPouzivatelaPodlaMena() {
        System.out.println("dajPouzivatelaPodlaMena");

        Person pouzivatel = userDao.findByName("Name1");
        Assert.assertEquals(new Long(1), pouzivatel.getId());
    }

    @Test
    public void testDajPouzivatelaPodlaId() {
        System.out.println("dajPouzivatelaPodlaId");

        Person p = userDao.findById(1L);
        Assert.assertEquals("Name1", p.getName());
    }

    @Test
    public void testUlozPouzivatelaPridaj() {
        System.out.println("ulozPouzivatela");
        
        Person person = new Person(null, "Name6", "Surname6", "City6", "Street 6", 6, "email6@domain.com");
        userDao.saveOrUpdate(person);
        
        String sql = "SELECT * FROM Person WHERE email = ?";
        Person p = jdbcTemplate.queryForObject(sql, userDao.rowMapper, "email6@domain.com");

        Assert.assertNotNull(p.getId());
        Assert.assertEquals("Name6", p.getName());
        Assert.assertEquals("Surname6", p.getSurname());
        Assert.assertEquals("City6", p.getCity());
        Assert.assertEquals("Street 6", p.getStreet());
    }

    @Test
    public void testUlozPouzivatelaUprav() {
        System.out.println("ulozPouzivatela");

        Person pouzivatel = new Person(2L, "Name0", "Surname0", "City0", "Street 0", 0, "email0@domain.com");
        userDao.saveOrUpdate(pouzivatel);
        
        String sql = "SELECT * FROM Person WHERE id = 2";
        Person p = jdbcTemplate.queryForObject(sql, userDao.rowMapper);

        Assert.assertEquals("Name0", p.getName());
        Assert.assertEquals("Surname0", p.getSurname());
        Assert.assertEquals("City0", p.getCity());
        Assert.assertEquals("Street 0", p.getStreet());
    }

    @Test
    public void testOdstranPouzivatela() {
        System.out.println("odstranPouzivatela");

        Person person = new Person(5L, null, null, null, null, 0, null);
        userDao.delete(person);
        
        String sql = "SELECT COUNT(*) FROM Person WHERE id = 5";        
        Assert.assertEquals(new Long(0), jdbcTemplate.queryForObject(sql, Long.class));
    }

}
