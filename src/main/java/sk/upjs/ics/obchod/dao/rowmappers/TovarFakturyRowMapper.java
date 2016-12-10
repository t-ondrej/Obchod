package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Tovar;

public class TovarFakturyRowMapper  implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Tovar tovar = new Tovar();
        tovar.setId(rs.getLong("id_tovar"));
        tovar.setNazov(rs.getString("nazov"));
        tovar.setIdKategoria(rs.getLong("id_kategoria"));
        tovar.setIdZnacka(rs.getLong("id_znacka"));
        tovar.setCena(rs.getInt("cena"));
        tovar.setPocetKusov(rs.getInt("pocet_kusov_vo_fakture"));
        
        return tovar;
    }
    
}
