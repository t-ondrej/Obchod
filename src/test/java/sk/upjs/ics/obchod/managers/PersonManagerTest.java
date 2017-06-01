package sk.upjs.ics.obchod.managers;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlPersonDao;
import sk.upjs.ics.obchod.dao.IPersonDao;
import sk.upjs.ics.obchod.entity.Person;

public class PersonManagerTest {

    private IPersonManager personManager;
    private JdbcTemplate jdbcTemplate;

    public PersonManagerTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
       
        IPersonDao pouzivatelDao = new MysqlPersonDao(jdbcTemplate);
        personManager = new PersonManager(pouzivatelDao);
    }

    
    @Test
    public void testHasFilledBillingAddress() {
        Person p1 = new Person(null, "N1", "S1", null, null, -1, null);
        Person p2 = new Person(null, "N2", "S2", "Cali", "Darkroot Garden 5", 365634, "n2@test.com");
        
        Assert.assertTrue(!personManager.hasFilledBillingAddress(p1));
        Assert.assertTrue(personManager.hasFilledBillingAddress(p2));
    }

    
}
