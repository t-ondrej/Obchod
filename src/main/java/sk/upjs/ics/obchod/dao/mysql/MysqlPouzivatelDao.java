package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.dao.rowmappers.PouzivatelRowMapper;
import sk.upjs.ics.obchod.entity.Pouzivatel;


public class MysqlPouzivatelDao implements PouzivatelDao{
    
    private final JdbcTemplate jdbcTemplate;
    
    public MysqlPouzivatelDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Pouzivatel> dajPouzivatelov() {
        String sql = "SELECT * FROM Pouzivatel";
               
        return jdbcTemplate.query(sql, new PouzivatelRowMapper());
    }
    
    @Override
    public Pouzivatel dajPouzivatela(String meno) {
        String sql = "SELECT * FROM Pouzivatel WHERE prihlasovacie_meno = ?";
        Pouzivatel pouzivatel;
        
        try {
           pouzivatel = jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper(), meno);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
        
        return pouzivatel;
    }
    
    @Override
    public Pouzivatel dajPouzivatela(Long id) {
        String sql = "SELECT * FROM Pouzivatel WHERE id = ?";
                   
        return jdbcTemplate.queryForObject(sql, new PouzivatelRowMapper(), id);
    }
    
    @Override
    public void pridajPouzivatela(Pouzivatel pouzivatel) {
        String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                + "VALUES(?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, pouzivatel.getPrihlasovacieMeno(), pouzivatel.getPasswordHash(), 
                pouzivatel.getSol(), pouzivatel.getEmail(), pouzivatel.getPoslednePrihlasenie(), pouzivatel.isJeAdministrator());
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
        String sql = "DELETE FROM Pouzivatel WHERE id = ?";
        
        jdbcTemplate.update(sql, pouzivatel.getId());
    }
    
    // TODO: upravit pouzivatelovi posledne prihlasenie

}






