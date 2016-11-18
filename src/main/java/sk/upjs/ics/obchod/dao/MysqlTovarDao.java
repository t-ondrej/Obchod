package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = "INSERT INTO Tovar (id_kategoria, id_podkategoria, nazov, znacka, cena, popis, obrazok_url) VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, tovar.getIdKategoria(), tovar.getIdPodkategoria(), 
                tovar.getNazov(), tovar.getZnacka(), tovar.getCena(), tovar.getPopis(), tovar.getobrazokUrl());
    }

    @Override
    public void odstranTovar(Tovar tovar) {
        String sql = "DELETE FROM Tovar WHERE id = ?";
        
        jdbcTemplate.update(sql, tovar.getId());
    }
    
}
