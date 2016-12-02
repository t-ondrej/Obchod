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
    public Long pridajKosikVratId() {
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
    public void odstranKosik(Long idKosik) {
        String sql = "DELETE FROM kosik WHERE id = ?";

        jdbcTemplate.update(sql, idKosik);
    }

    @Override
    public void dajTovarDoKosika(Long idTovaru, Long idKosika) {
         String sql = "INSERT INTO tovar_kosika (id_kosik, id_tovar, pocet_kusov) VALUES(?, ?, ?)";
        
        jdbcTemplate.update(sql, idKosika, idTovaru, 1);
    }    

    @Override
    public void odoberTovarZKosika(Long idTovaru, Long idKosika) {
        String sql = "DELETE FROM tovar_kosika WHERE id_tovar = ? AND id_kosik = ?;";
         jdbcTemplate.update(sql, idTovaru , idKosika);
    }
    
    @Override
    public List<Tovar> dajTovaryKosika(Long idKosika) {
        String sql = "SELECT t.id, t.id_kategoria, t.id_znacka, t.nazov, t.cena, t.popis, t.obrazok_url, t.pocet_kusov FROM tovar as t JOIN tovar_kosika as tk ON t.id=tk.id_tovar WHERE tk.id_kosik = ?;";
         BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
        return jdbcTemplate.query(sql, mapper, idKosika);
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Long idTovaru, Long idKosika) {
       String sql = "SELECT pocet_kusov FROM tovar_kosika WHERE id_kosik = ? AND id_tovar = ?;";
       return jdbcTemplate.queryForObject(sql,Integer.class, idKosika, idTovaru);
       
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Long idTovaru, Long idKosika, int pocet_kusov) {
        String sql = "UPDATE tovar_kosika SET pocet_kusov = ? WHERE id_kosik = ? AND id_tovar = ?;";
        jdbcTemplate.update(sql, pocet_kusov, idKosika , idTovaru);
    }
}
