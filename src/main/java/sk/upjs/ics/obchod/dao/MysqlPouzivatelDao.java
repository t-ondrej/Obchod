
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Pouzivatel;


public class MysqlPouzivatelDao implements PouzivatelDao{
    
    private final JdbcTemplate jdbcTemplate;
    
    public MysqlPouzivatelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Pouzivatel> dajPouzivatelov() {
        String sql = "SELECT * FROM Pouzivatel";
        
        BeanPropertyRowMapper<Pouzivatel> mapper = BeanPropertyRowMapper.newInstance(Pouzivatel.class);
        
        return jdbcTemplate.query(sql, mapper);
    }
    
    @Override
    public void pridajPouzivatela(Pouzivatel pouzivatel) {
        String sql = "INSERT INTO Tovar VALUES(? ? ? ? ? ? ?)";
        
        jdbcTemplate.update(sql, pouzivatel.getId(), pouzivatel.getKosik().getId(), pouzivatel.getPrihlasovacieMeno(), 
                pouzivatel.getPasswordHash(), pouzivatel.getSol(), pouzivatel.getEmail(), pouzivatel.getPoslednePrihlasenie());
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
        String sql = "DELETE FROM Pouzivatel WHERE id = ?";
        
        jdbcTemplate.update(sql, pouzivatel.getId());
    }
}






