package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IBrandDao;

public class MysqlBrandDao extends MysqlEntityDao<Brand> implements IBrandDao {

    public MysqlBrandDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Brand", BeanPropertyRowMapper.newInstance(Brand.class));
    }

    @Override
    public List<Brand> getAll() {
        String sql = "SELECT * FROM Brand";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Brand findById(Long brandId) {
        String sql = "SELECT * FROM Brand WHERE id = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, brandId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }       
    }

    @Override
    public Brand findByName(String brandName) {
        String sql = "SELECT * FROM Brand WHERE `name` = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, brandName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }      
    }

    @Override
    public void saveOrUpdate(Brand brand) {
        entityFields.put("id", brand.getId());
        entityFields.put("`name`", brand.getName());
        super.saveOrUpdate(brand);       
    }

}
