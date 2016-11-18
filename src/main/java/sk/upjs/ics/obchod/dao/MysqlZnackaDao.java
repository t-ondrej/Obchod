
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlZnackaDao implements ZnackaDao{
    
     private final JdbcTemplate jdbcTemplate;
    
    public MysqlZnackaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Znacka> dajZnacky() {
       String sql = "SELECT * FROM Znacka";        
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);        
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Znacka najdiPodlaId(Long id_Znacka) {
        String sql = "SELECT * FROM Znacka WHERE id = ?";        
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);        
        return jdbcTemplate.queryForObject(sql, mapper, id_Znacka);
    }

    @Override
    public void uloz(Znacka znacka) {
        String sql = "INSERT INTO Znacka (nazov) VALUES(?) ";
       jdbcTemplate.update(sql, znacka.getNazov());}
    
}
