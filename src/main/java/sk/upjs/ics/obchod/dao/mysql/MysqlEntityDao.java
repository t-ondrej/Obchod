package sk.upjs.ics.obchod.dao.mysql;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import sk.upjs.ics.obchod.entity.Entity;
import sk.upjs.ics.obchod.dao.IEntityDao;

public abstract class MysqlEntityDao<T extends Entity> implements IEntityDao<T> {

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    protected JdbcTemplate jdbcTemplate;
    
    protected String tableName;
    
    protected RowMapper<T> rowMapper;
    
    protected LinkedHashMap<String, Object> entityFields;
    
    public MysqlEntityDao(JdbcTemplate jdbcTemplate, String tableName, RowMapper<T> rowMapper) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.rowMapper = rowMapper;
        this.entityFields = new LinkedHashMap<>();
    }
    
    @Override
    public List<T> getAll() {
        String sql = "SELECT * FROM " + tableName;
        
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void saveOrUpdate(T entity) {
        
        if (entity.getId() > 0) {
            
            String fields = entityFields.keySet()
                .stream()
                .map(s -> s + " = :" + s)
                .collect(Collectors.joining(", "));
        
            String sql = "UPDATE " + tableName + " SET " + fields + 
                    " WHERE id = " + entity.getId();
            
            namedParameterJdbcTemplate.update(sql, entityFields);
            
        } else {
            String fields = this.entityFields.keySet()
                    .stream()
                    .collect(Collectors.joining(", :", ":", ""));
            
            KeyHolder keyHolder = new GeneratedKeyHolder();
            
            String sql = "INSERT INTO " + tableName + " VALUES(" + fields + ")";
            
            namedParameterJdbcTemplate.update(sql,
                    new MapSqlParameterSource(entityFields),
                    keyHolder);
            
            Long id = keyHolder.getKey().longValue();
            entity.setId(id);
        }        
        
    }

    @Override
    public void delete(Entity entity) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";

        jdbcTemplate.update(sql, entity.getId());
    }

    @Override
    public T findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }       
    }
}
