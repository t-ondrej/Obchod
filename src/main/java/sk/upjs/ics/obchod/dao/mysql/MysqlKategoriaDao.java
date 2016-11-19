
package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.KategoriaDao;
import sk.upjs.ics.obchod.entity.Kategoria;

public class MysqlKategoriaDao implements KategoriaDao{
    
    private final JdbcTemplate jdbcTemplate;
    
    public MysqlKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Kategoria> dajKategorie() {
       String sql = "SELECT * FROM Kategoria";        
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);        
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Kategoria najdiPodlaId(Long idKategoria) {
       String sql = "SELECT * FROM kategoria WHERE id = ?";        
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);        
        return jdbcTemplate.queryForObject(sql, mapper, idKategoria);
    }

    @Override
    public void uloz(Kategoria kategoria) {
       String sql = "INSERT INTO kategoria (nazov) VALUES(?) ";
       jdbcTemplate.update(sql, kategoria.getNazov());
    }
    
}
