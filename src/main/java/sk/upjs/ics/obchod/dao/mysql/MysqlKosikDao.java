package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class MysqlKosikDao implements KosikDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlKosikDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Kosik> dajKosiky() {
        String sql = "SELECT * FROM kosik";

        BeanPropertyRowMapper<Kosik> mapper = BeanPropertyRowMapper.newInstance(Kosik.class);

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Kosik dajKosikPodlaId(Long idKosika) {
        String sql = "SELECT * FROM kosik WHERE id = ?";

        BeanPropertyRowMapper<Kosik> mapper = BeanPropertyRowMapper.newInstance(Kosik.class);

        return jdbcTemplate.queryForObject(sql, mapper, idKosika);
    }

    @Override
    public Long pridajKosikVratId(Kosik kosik) {
        String sql = "INSERT INTO kosik VALUES()";
        Long idKosik = -1L;
        
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.execute();

            ResultSet rs = stm.getGeneratedKeys();
            System.out.println(rs.toString());
            if (rs.next()) {
                idKosik = rs.getLong(1);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idKosik;
    }

    @Override
    public void odstranKosik(Kosik kosik) {
        String sql = "DELETE FROM kosik WHERE id = ?";

        jdbcTemplate.update(sql, kosik.getId());
    }

    @Override
    public void dajTovarDoKosika(Long idTovaru, Long idKosika) {
         String sql = "INSERT INTO tovar_kosika (id_kosik, id_tovar) VALUES(?, ?)";
        
        jdbcTemplate.update(sql, idKosika, idTovaru);
    }

    @Override
    public List<Tovar> dajTovaryKosika(Long idKosika) {
        String sql = "SELECT id, id_kategoria, id_znacka, nazov, cena, popis, obrazok_url, pocet_kusov FROM tovar as t JOIN tovar_kosika as tk ON t.id=tk.id_tovar WHERE tk.id_kosik = ?;";
         BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        return jdbcTemplate.query(sql, mapper, idKosika);
    }

}
