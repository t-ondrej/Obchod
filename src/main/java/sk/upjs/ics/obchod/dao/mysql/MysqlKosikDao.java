package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.entity.Kosik;

public class MysqlKosikDao implements KosikDao {
    
    private final JdbcTemplate jdbcTemplate;

    public MysqlKosikDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
   
    @Override
    public List<Kosik> dajKosiky() {
        String sql = "SELECT * FROM kosik";
        
        BeanPropertyRowMapper<Kosik> mapper = BeanPropertyRowMapper.newInstance(Kosik.class);
        
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Kosik dajKosikPodlaId(Long idKosika) {
        String sql = "SELECT * FROM kosik WHERE id = ?";
        
        BeanPropertyRowMapper<Kosik> mapper = BeanPropertyRowMapper.newInstance(Kosik.class);
        
        return jdbcTemplate.queryForObject(sql, mapper, idKosika);
    }

    @Override
    public void pridajKosik(Kosik kosik) {
        String sql = "INSERT INTO kosik(id) VALUES (?)";
        
        jdbcTemplate.update(sql, kosik.getId());
    }

    @Override
    public void odstranKosik(Kosik kosik) {
        String sql = "DELETE FROM kosik WHERE id = ?";
        
        jdbcTemplate.update(sql, kosik.getId());
    }
    
}
