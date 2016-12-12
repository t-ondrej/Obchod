package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class TovarRowMapper implements RowMapper {
    
    @Override
    public Tovar mapRow(ResultSet rs, int i) throws SQLException {
        Tovar tovar = new Tovar();
        tovar.setId(rs.getLong("id_tovar"));
        tovar.setNazov(rs.getString("nazov_tovar"));
        
        Kategoria kategoria = new Kategoria();
        kategoria.setId(rs.getLong("id_kategoria"));
        kategoria.setNazov(rs.getString("nazov_kategoria"));
        tovar.setKategoria(kategoria);
        
        Znacka znacka = new Znacka();
        znacka.setId(rs.getLong("id_znacka"));
        znacka.setNazov(rs.getString("nazov_znacka"));
        tovar.setZnacka(znacka);
        
        tovar.setCena(rs.getInt("cena"));
        tovar.setPopis(rs.getString("popis"));
        tovar.setObrazokUrl(rs.getString("obrazok_url"));
        tovar.setPocetKusov(rs.getInt("pocet_kusov"));
        
        return tovar;
    }
    
}
