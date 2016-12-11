package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PouzivatelRowMapper implements RowMapper<Pouzivatel> {

    @Override
    public Pouzivatel mapRow(ResultSet rs, int i) throws SQLException {
        Pouzivatel pouzivatel = new Pouzivatel();
        Kosik kosik = new Kosik();        
        pouzivatel.setId(rs.getLong("id"));
        pouzivatel.setPrihlasovacieMeno(rs.getString("prihlasovacie_meno"));
        pouzivatel.setMeno(rs.getString("meno"));
        pouzivatel.setPriezvisko(rs.getString("priezvisko"));
        pouzivatel.setMesto(rs.getString("mesto"));
        pouzivatel.setUlica(rs.getString("ulica"));
        pouzivatel.setPsc(rs.getInt("psc"));
        pouzivatel.setPasswordHash(rs.getString("hash_hesla"));
        pouzivatel.setSol(rs.getString("sol"));
        pouzivatel.setEmail(rs.getString("email"));
        LocalTime time = LocalTime.now();
        pouzivatel.setPoslednePrihlasenie(LocalDateTime.of(rs.getDate("posledne_prihlasenie").toLocalDate(), time));
        pouzivatel.setJeAdministrator(rs.getBoolean("je_administrator"));
        pouzivatel.setKosik(kosik);
        return pouzivatel;
    }
    
}
