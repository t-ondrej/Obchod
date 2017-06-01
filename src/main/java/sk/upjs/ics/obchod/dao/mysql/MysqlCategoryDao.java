package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.dao.ICategoryDao;

public class MysqlCategoryDao extends MysqlEntityDao<Category> implements ICategoryDao {

    public MysqlCategoryDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Category", BeanPropertyRowMapper.newInstance(Category.class));
    }

    @Override
    public List<Category> getAll() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Category findById(Long categoryId) {
        String sql = "SELECT * FROM Category WHERE id = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, categoryId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }         
    }

    @Override
    public Category findByName(String categoryName) {
        String sql = "SELECT * FROM Category WHERE `name` = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, categoryName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }      
    }

    @Override
    public void saveOrUpdate(Category category) {
        entityFields.put("id", category.getId());
        entityFields.put("`name`", category.getName());
        super.saveOrUpdate(category);
    }
}
