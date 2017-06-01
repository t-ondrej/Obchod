package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDate;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Account;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.utils.TestDataProvider;

public class MysqlAccountDaoTest {
    
    private MysqlAccountDao accountDao;
    private JdbcTemplate jdbcTemplate;
    
    public MysqlAccountDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        accountDao = new MysqlAccountDao(jdbcTemplate);
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
    public void testUpdateLastLogin() {        
        Account acc = new Account(1L, null, null, null, null, null);       
        accountDao.updateLastLogin(acc);
        
        String sql = "SELECT last_sign_in FROM Account WHERE id = 1";
        LocalDate currDate = jdbcTemplate.queryForObject(sql, LocalDate.class);
        assertEquals(LocalDate.now(), currDate);
    }

    @Test
    public void testFindByUsername() {
        Account acc = accountDao.findByUsername("Username1");
        
        assertEquals("Username1", acc.getUsername());
    }

    @Test
    public void testSaveOrUpdate() {
        Person p1 = new Person(2L, null, null, null, null, 1, null);
        Account updatedAcc = new Account(2L, "Username22", "hash", "salt", null, p1);
        accountDao.saveOrUpdate(updatedAcc);
        
        String sql = "SELECT * FROM Account WHERE id = 2";
        updatedAcc = jdbcTemplate.queryForObject(sql, accountDao.rowMapper);
        assertEquals("Username22", updatedAcc.getUsername());
        
        Person p2 = new Person(6L, null, null, null, null, 1, null);
        Account newAccount = new Account("Username6", "pw", null, p2);
        accountDao.saveOrUpdate(newAccount);
        assertNotNull(newAccount.getId());
    }
    
    @Test
    public void testDelete() {
        Account acc = new Account(5L, null, null, null, null, null);
        accountDao.delete(acc);
        
        String sql = "SELECT COUNT(*) FROM Account WHERE id = 5";
        int count = jdbcTemplate.queryForObject(sql, int.class);
        
        assertTrue(count == 0);
    }
    
    @Test
    public void testFindById() {
        Account acc = accountDao.findById(1L);       
        assertEquals("Username1", acc.getUsername());        
    }
    
    @Test
    public void testGetAll() {
        List<Account> accounts = accountDao.getAll();
        accounts.forEach(account -> assertNotNull(account.getId()));
        assertTrue(accounts.size() > 1);
    }
    
}
