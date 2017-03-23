package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.rowmappers.TovarRowMapper;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.dao.ITovarDao;

public class MysqlTovarDao implements ITovarDao {

    private final JdbcTemplate jdbcTemplate;

    private final TovarRowMapper mapper = new TovarRowMapper();

    private final String vyberTovarSql = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";

    public MysqlTovarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tovar> dajTovar() {
        return jdbcTemplate.query(this.vyberTovarSql, mapper);
    }

    @Override
    public List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria) {
        String sql = vyberTovarSql + "WHERE id_kategoria = ?";

        return jdbcTemplate.query(sql, mapper, kategoria.getId());
    }

    @Override
    public Tovar dajTovarPodlaNazvu(String nazov) {
        String sql = vyberTovarSql + "WHERE T.nazov = ?";

        return (Tovar) jdbcTemplate.queryForObject(sql, mapper, nazov);
    }

    @Override
    public List<Tovar> dajTovarPodlaZnacky(Znacka znacka) {
        String sql = vyberTovarSql + "WHERE id_znacka = ?";

        return jdbcTemplate.query(sql, mapper, znacka.getId());
    }

    @Override
    public Long ulozTovar(Tovar tovar) {

        if (tovar.getId() == 0) {
            String sql = "INSERT INTO Tovar ( nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov) VALUES(?, ?, ?, ?, ?, ?, ?)";

            Long idTovar = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, tovar.getNazov());
                stm.setLong(2, tovar.getKategoria().getId());
                stm.setLong(3, tovar.getZnacka().getId());
                stm.setInt(4, tovar.getCena());
                stm.setString(5, tovar.getPopis());
                stm.setString(6, tovar.getObrazokUrl());
                stm.setInt(7, tovar.getPocetKusov());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idTovar = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return idTovar;

        } else {
            String sql = "UPDATE tovar SET nazov = ?, id_kategoria = ?, id_znacka = ?, c"
                    + "ena = ?, popis = ?, obrazok_url = ?, pocet_kusov = ? "
                    + "WHERE id = ?;";
            jdbcTemplate.update(sql, tovar.getNazov(), tovar.getKategoria().getId(),
                    tovar.getZnacka().getId(), tovar.getCena(), tovar.getPopis(), 
                    tovar.getObrazokUrl(), tovar.getPocetKusov(), tovar.getId());

            return tovar.getId();
        }
    }

    @Override
    public void odstranTovar(Tovar tovar) {
        String sql = "DELETE FROM Tovar WHERE id = ?";

        jdbcTemplate.update(sql, tovar.getId());
    }

    @Override
    public Tovar najdiPodlaId(Long id) {
        String sql = vyberTovarSql + "WHERE T.id = ?";
        return (Tovar) jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public void nastavTovaruPocetKusov(Tovar tovar, int pocet) {

        String sql = "UPDATE Tovar SET pocet_kusov = ? WHERE id = ?";
        jdbcTemplate.update(sql, pocet, tovar.getId());

    }

    @Override
    public int dajPocetTovaru(Tovar tovar) {
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, tovar.getId());
    }
}
