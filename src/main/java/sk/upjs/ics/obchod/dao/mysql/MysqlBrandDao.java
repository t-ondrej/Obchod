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
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IBrandDao;

public class MysqlBrandDao implements IBrandDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlBrandDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Brand> getAll() {
        String sql = "SELECT * FROM Znacka";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Brand findById(Long brandId) {
        String sql = "SELECT * FROM Znacka WHERE id = ?";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        return jdbcTemplate.queryForObject(sql, mapper, brandId);
    }

    @Override
    public Brand findByName(String brandName) {
        String sql = "SELECT * FROM Znacka WHERE nazov = ?";
        BeanPropertyRowMapper<Brand> mapper = BeanPropertyRowMapper.newInstance(Brand.class);
        try {
            return jdbcTemplate.queryForObject(sql, mapper, brandName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long saveOrUpdate(Brand brand) {

        if (brand.getId() == 0) {
            String sql = "INSERT INTO Znacka (nazov) VALUES(?) ";

            Long brandId = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, brand.getName());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    brandId = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return brandId;
        } else {
            String sql = "UPDATE znacka SET nazov=? WHERE id = ?;";
            jdbcTemplate.update(sql, brand.getName(), brand.getId());

            return brand.getId();
        }
    }

    @Override
    public void delete(Brand brand) {
        String sql = "DELETE FROM znacka WHERE id = ?";
        jdbcTemplate.update(sql, brand.getId());
    }

}
