package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.rowmappers.TovarFakturyRowMapper;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.dao.IFakturaDao;

public class MysqlFakturaDao implements IFakturaDao {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Faktura> mapper;

    public MysqlFakturaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = BeanPropertyRowMapper.newInstance(Faktura.class);
    }

    @Override
    public List<Faktura> dajFaktury() {
        String sql = "SELECT * FROM Faktura";

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Faktura> dajFakturyZaPoslednyDen() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Faktura> dajFakturyZaPoslednyTyzden() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 WEEK AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Faktura> dajFakturyZaPoslednyMesiac() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 MONTH AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Faktura> dajFakturyZaPoslednyRok() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 YEAR AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Long pridajFakturu(Faktura faktura) {
        String sql = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(?,?,?)";

        Long idFaktura = -1L;

        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.setLong(1, faktura.getIdPouzivatel());
            stm.setInt(2, faktura.getSuma());
            stm.setObject(3, faktura.getDatumNakupu());
            stm.execute();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                idFaktura = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idFaktura;
    }

    @Override
    public void odstranFakturu(Faktura faktura) {
        String sql = "DELETE FROM Faktura WHERE id = ?";

        jdbcTemplate.update(sql, faktura.getId());
    }

    @Override
    public void pridajTovarFakture(Tovar tovar, Faktura faktura, int pocetTovaru) {
        String sql = "INSERT INTO Tovar_Faktury(id_faktura, nazov_tovaru, nazov_kategorie, nazov_znacky, pocet_kusov_tovaru, cena) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, faktura.getId(), tovar.getNazov(), tovar.getKategoria().getNazov(), tovar.getZnacka().getNazov(), pocetTovaru, tovar.getCena() * pocetTovaru);
    }

    @Override
    public List<Tovar> dajTovarFaktury(Faktura faktura) {
        String sql = "SELECT * FROM tovar_faktury WHERE id_faktura = ?";

        return jdbcTemplate.query(sql, new TovarFakturyRowMapper(), faktura.getId());
    }

}
