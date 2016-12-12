package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;

public class TovarFakturyRowMapper  implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Tovar tovarFaktury = new Tovar();
        tovarFaktury.setNazov(rs.getString("nazov_tovaru"));
        
        Kategoria kategoria = new Kategoria();
        kategoria.setNazov(rs.getString("nazov_kategorie"));
        tovarFaktury.setKategoria(kategoria);
        
        Znacka znacka = new Znacka();
        znacka.setNazov(rs.getString("nazov_znacky"));
        tovarFaktury.setZnacka(znacka);
        
        tovarFaktury.setCena(rs.getInt("cena"));
        tovarFaktury.setPocetKusov(rs.getInt("pocet_kusov_tovaru"));
        
        return tovarFaktury;
    }
    
}
