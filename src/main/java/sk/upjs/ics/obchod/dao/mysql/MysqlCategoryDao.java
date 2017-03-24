package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.dao.ICategoryDao;

public class MysqlCategoryDao implements ICategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlCategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> getAll() {
        String sql = "SELECT * FROM Kategoria";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Category finById(Long categoryId) {
        String sql = "SELECT * FROM kategoria WHERE id = ?";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        return jdbcTemplate.queryForObject(sql, mapper, categoryId);
    }

    @Override
    public Category findByName(String categoryName) {
        String sql = "SELECT * FROM kategoria WHERE nazov = ?";
        BeanPropertyRowMapper<Category> mapper = BeanPropertyRowMapper.newInstance(Category.class);
        try {
            return jdbcTemplate.queryForObject(sql, mapper, categoryName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long saveOrUpdate(Category category) {

        if (category.getId() == 0) {
            String sql = "INSERT INTO kategoria (nazov) VALUES(?) ";

            Long categoryId = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, category.getName());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    categoryId = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return categoryId;
        } else {
            String sql = "UPDATE kategoria SET nazov=? WHERE id = ?;";
            jdbcTemplate.update(sql, category.getName(), category.getId());

            return category.getId();
        }
    }

    @Override
    public void delete(Category category) {
        String sql = "DELETE FROM kategoria WHERE id = ?";
        jdbcTemplate.update(sql, category.getId());
    }

}
