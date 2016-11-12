
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.entity.Pouzivatel;


public class MysqlPouzivatelDAO implements PouzivatelDAO{
    
    private final JdbcTemplate jdbcTemplate;
    
    public MysqlPouzivatelDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    

    @Override
    public List<Pouzivatel> dajPouzivatelov() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pridajPouzivatela(Pouzivatel pouzivatel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void aktualizujPouzivatelov() {
        // neviem celkom co to ma robit
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
