package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.ZnackaDao;
import sk.upjs.ics.obchod.entity.Znacka;

public class MysqlZnackaDao implements ZnackaDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlZnackaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Znacka> dajZnacky() {
        String sql = "SELECT * FROM Znacka";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Znacka najdiPodlaId(Long idZnacka) {
        String sql = "SELECT * FROM Znacka WHERE id = ?";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        return jdbcTemplate.queryForObject(sql, mapper, idZnacka);
    }

    @Override
    public Znacka najdiPodlaNazvu(String nazovZnacky) {
        String sql = "SELECT * FROM Znacka WHERE nazov = ?";
        BeanPropertyRowMapper<Znacka> mapper = BeanPropertyRowMapper.newInstance(Znacka.class);
        try {
            return jdbcTemplate.queryForObject(sql, mapper, nazovZnacky);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long uloz(Znacka znacka) {

        if (znacka.getId() == 0) {
            String sql = "INSERT INTO Znacka (nazov) VALUES(?) ";

            Long idZnacka = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, znacka.getNazov());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idZnacka = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return idZnacka;
        } else {
            String sql = "UPDATE znacka SET nazov=? WHERE id = ?;";
            jdbcTemplate.update(sql, znacka.getNazov(), znacka.getId());

            return znacka.getId();
        }
    }

    @Override
    public void odstranZnacku(Znacka znacka) {
        String sql = "DELETE FROM znacka WHERE id = ?";
        jdbcTemplate.update(sql, znacka.getId());
    }

}
