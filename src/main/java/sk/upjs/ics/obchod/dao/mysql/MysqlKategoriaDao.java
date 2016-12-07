package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.KategoriaDao;
import sk.upjs.ics.obchod.entity.Kategoria;

public class MysqlKategoriaDao implements KategoriaDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlKategoriaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Kategoria> dajKategorie() {
        String sql = "SELECT * FROM Kategoria";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Kategoria najdiPodlaId(Long idKategoria) {
        String sql = "SELECT * FROM kategoria WHERE id = ?";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        return jdbcTemplate.queryForObject(sql, mapper, idKategoria);
    }

    @Override
    public Kategoria najdiPodlaNazvu(String nazovKategorie) {
        String sql = "SELECT * FROM kategoria WHERE nazov = ?";
        BeanPropertyRowMapper<Kategoria> mapper = BeanPropertyRowMapper.newInstance(Kategoria.class);
        return jdbcTemplate.queryForObject(sql, mapper, nazovKategorie);
    }

    @Override
    public Long uloz(Kategoria kategoria) {
        
        if(kategoria.getId() == 0){
            String sql = "INSERT INTO kategoria (nazov) VALUES(?) ";     

            Long idKategoria = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, kategoria.getNazov());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();            
                if (rs.next()) {
                    idKategoria = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return idKategoria; 
        }else{
            String sql = "UPDATE kategoria SET nazov=? WHERE id = ?;";
            jdbcTemplate.update(sql, kategoria.getNazov(), kategoria.getId());           
            
            return kategoria.getId();
        }
    }

    @Override
    public void odstranKategoriu(Kategoria kategoria) {
       String sql = "DELETE FROM kategoria WHERE id = ?";                
       jdbcTemplate.update(sql, kategoria.getId());
    }

}
