package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.FakturaDao;
import sk.upjs.ics.obchod.entity.Faktura;

public class MysqlFakturaDao implements FakturaDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlFakturaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Faktura> dajFaktury() {
        String sql = "SELECT * FROM Faktura";
        
        BeanPropertyRowMapper<Faktura> mapper = BeanPropertyRowMapper.newInstance(Faktura.class);
        
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public void pridajFakturu(Faktura faktura) {
        String sql = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(?,?,?)";
        
        jdbcTemplate.update(sql, faktura.getIdPouzivatel(), faktura.getSuma(), faktura.getDatumNakupu());
    }

    @Override
    public void odstranFakturu(Faktura faktura) {
        String sql = "DELETE FROM Faktura WHERE id = ?";
                
        jdbcTemplate.update(sql, faktura.getId());
    }
    
}
