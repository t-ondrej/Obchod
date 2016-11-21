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
    public void odstranKosik(Kosik kosik
    ) {
        String sql = "DELETE FROM kosik WHERE id = ?";

        jdbcTemplate.update(sql, kosik.getId());
    }

}
