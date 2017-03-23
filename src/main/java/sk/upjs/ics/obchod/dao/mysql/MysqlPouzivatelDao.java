package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.rowmappers.PouzivatelRowMapper;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.dao.IPouzivatelDao;

public class MysqlPouzivatelDao implements IPouzivatelDao {

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
        } catch (IncorrectResultSizeDataAccessException e) {
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
    public Long ulozPouzivatela(Pouzivatel pouzivatel) {

        if (pouzivatel.getId() == 0) {
            String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, meno, priezvisko, mesto, ulica, psc, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Long idPouzivatel = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, pouzivatel.getPrihlasovacieMeno());
                stm.setString(2, pouzivatel.getMeno());
                stm.setString(3, pouzivatel.getPriezvisko());
                stm.setString(4, pouzivatel.getMesto());
                stm.setString(5, pouzivatel.getUlica());
                stm.setInt(6, pouzivatel.getPsc());
                stm.setString(7, pouzivatel.getPasswordHash());
                stm.setString(8, pouzivatel.getSol());
                stm.setString(9, pouzivatel.getEmail());
                stm.setObject(10, pouzivatel.getPoslednePrihlasenie());
                stm.setBoolean(11, pouzivatel.isJeAdministrator());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idPouzivatel = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return idPouzivatel;
        } else {
            
            String sql = "UPDATE Pouzivatel SET prihlasovacie_meno = ?, meno = ?, priezvisko = ?, "
                    + "mesto = ?, ulica = ?, psc = ?, hash_hesla = ?, sol = ?, email = ?, "
                    + "posledne_prihlasenie = ?, je_administrator = ? WHERE id = ?;";
            
            jdbcTemplate.update(sql, pouzivatel.getPrihlasovacieMeno(), pouzivatel.getMeno(),
                    pouzivatel.getPriezvisko(), pouzivatel.getMesto(), pouzivatel.getUlica(),
                    pouzivatel.getPsc(), pouzivatel.getPasswordHash(), pouzivatel.getSol(),
                    pouzivatel.getEmail(), pouzivatel.getPoslednePrihlasenie(),
                    pouzivatel.isJeAdministrator(), pouzivatel.getId());

            return pouzivatel.getId();
        }
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
        String sql = "DELETE FROM Pouzivatel WHERE id = ?";

        jdbcTemplate.update(sql, pouzivatel.getId());
    }

    @Override
    public void novePoslednePrihlasenie(Pouzivatel pouzivatel) {
        String sql = "UPDATE Pouzivatel SET posledne_prihlasenie = ? WHERE id = ?;";
        jdbcTemplate.update(sql, LocalDateTime.now(), pouzivatel.getId());
    }

}
