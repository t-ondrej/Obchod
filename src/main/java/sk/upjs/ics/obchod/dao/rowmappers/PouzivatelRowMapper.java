package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PouzivatelRowMapper implements RowMapper<Pouzivatel> {

    @Override
    public Pouzivatel mapRow(ResultSet rs, int i) throws SQLException {
        Pouzivatel pouzivatel = new Pouzivatel();
        Kosik kosik = new Kosik();
        kosik.setId(rs.getLong("id_kosik"));
        pouzivatel.setId(rs.getLong("id"));
        pouzivatel.setPrihlasovacieMeno(rs.getString("prihlasovacie_meno"));
        pouzivatel.setSol(rs.getString("sol"));
        pouzivatel.setEmail(rs.getString("email"));
        pouzivatel.setPoslednePrihlasenie(rs.getDate("posledne_prihlasenie"));
        pouzivatel.setKosik(kosik);
        return pouzivatel;
    }
    
}
