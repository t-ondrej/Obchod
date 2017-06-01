package sk.upjs.ics.obchod.dao.mysql;

import java.time.LocalDateTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import sk.upjs.ics.obchod.dao.IAccountDao;
import sk.upjs.ics.obchod.entity.Account;

public class MysqlAccountDao extends MysqlEntityDao<Account> implements IAccountDao {

    public MysqlAccountDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Account", BeanPropertyRowMapper.newInstance(Account.class));
    }
    
    @Override
    public void updateLastLogin(Account account) {
        String sql = "UPDATE Account SET last_sign_in = ? WHERE id = ?;";
        jdbcTemplate.update(sql, LocalDateTime.now(), account.getId());
    }
    
    @Override
    public Account findByUsername(String username) {
        String sql = "SELECT * FROM account WHERE username = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public void saveOrUpdate(Account account) {
        entityFields.put("id", account.getId());
        entityFields.put("username", account.getUsername());
        entityFields.put("password_hash", account.getPasswordHash());
        entityFields.put("salt", account.getSalt());
        entityFields.put("last_sign_in", account.getLastLogin());
        entityFields.put("is_administrator", account.isAdministrator());
        entityFields.put("id_person", account.getPerson().getId());
        super.saveOrUpdate(account);
    }
    
}
