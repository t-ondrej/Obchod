package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.dao.IPersonDao;

public class MysqlPersonDao extends MysqlEntityDao<Person>implements IPersonDao {


    public MysqlPersonDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Person", personRowMapper());
    }

    @Override
    public List<Person> getAll() {
        String sql = "SELECT * FROM Person";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Person findByName(String name) {
        String sql = "SELECT * FROM Person WHERE `name` = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }    
    }

    @Override
    public Person findById(Long id) {
        String sql = "SELECT * FROM Person WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }      
    }

    @Override
    public void saveOrUpdate(Person person) {
        entityFields.put("id", person.getId());
        entityFields.put("`name`", person.getName());
        entityFields.put("surname", person.getSurname());
        entityFields.put("city", person.getCity());
        entityFields.put("street", person.getStreet());
        entityFields.put("postal_code", person.getPostalCode());
        entityFields.put("email", person.getEmail());
        super.saveOrUpdate(person);
    }
    
    private static RowMapper<Person> personRowMapper() {
        return (rs, i) -> new Person(rs.getLong("id"), 
                rs.getString("name"), 
                rs.getString("surname"),
                rs.getString("city"),
                rs.getString("street"),
                rs.getInt("postal_code"),
                rs.getString("email"));
    }
}
