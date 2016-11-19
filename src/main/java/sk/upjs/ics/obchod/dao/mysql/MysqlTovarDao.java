package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Tovar;


public class MysqlTovarDao implements TovarDao{

    private final JdbcTemplate jdbcTemplate;
    
    public MysqlTovarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Tovar> dajTovar() {
        String sql = "SELECT * FROM Tovar";
        
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public void pridajTovar(Tovar tovar) {
        String sql = "INSERT INTO Tovar (id_kategoria, nazov, id_znacka, cena, popis, obrazok_url) VALUES(?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, tovar.getIdKategoria(), tovar.getNazov(), 
                tovar.getIdZnacka(), tovar.getCena(), tovar.getPopis(), tovar.getobrazokUrl());
    }

    @Override
    public void odstranTovar(Tovar tovar) {
        String sql = "DELETE FROM Tovar WHERE id = ?";
        
        jdbcTemplate.update(sql, tovar.getId());
    }
    
}
