package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;


public class MysqlTovarDao implements TovarDao{

    private final JdbcTemplate jdbcTemplate;
    
    private final BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);
           
    public MysqlTovarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Tovar> dajTovary() {
        String sql = "SELECT * FROM Tovar";
                    
        return jdbcTemplate.query(sql, mapper);
    }
    
      @Override
    public List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria) {
        String sql = "SELECT * FROM Tovar WHERE id_kategoria = ?";
        
        return jdbcTemplate.query(sql, mapper, kategoria.getId());
    }

    @Override
    public Tovar dajTovarPodlaNazvu(String nazov) {
        String sql = "SELECT * FROM Tovar WHERE nazov = ?";
        
        return jdbcTemplate.queryForObject(sql, mapper, nazov);
    }

    @Override
    public List<Tovar> dajTovarPodlaZnacky(Znacka znacka) {
        String sql = "SELECT * FROM Tovar WHERE id_znacka = ?";
        
        return jdbcTemplate.query(sql, mapper, znacka.getId());
    }   

    @Override
    public Long pridajTovar(Tovar tovar) {
        String sql = "INSERT INTO Tovar ( nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov) VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        Long idTovar = -1L;
        
        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stm.setString(1, tovar.getNazov());
            stm.setLong(2, tovar.getIdKategoria());
            stm.setLong(3, tovar.getIdZnacka());
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
    }

    @Override
    public void odstranTovar(Long idTovaru) {
        String sql = "DELETE FROM Tovar WHERE id = ?";
        
        jdbcTemplate.update(sql, idTovaru);
    }

    @Override
    public Tovar najdiPodlaId(Long id) {
        String sql = "SELECT * FROM tovar WHERE id = ?";        
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);        
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public void nastavTovaruPocetKusov(Long idTovar, int pocet) {       
       
       String sql = "UPDATE Tovar SET pocet_kusov = ? WHERE id = ?"; 
       jdbcTemplate.update(sql, pocet, idTovar);       
       
    }

    @Override
    public int dajPocetTovaru(Long idTovar) {
       String sql = "SELECT pocet_kusov FROM tovar WHERE id = ?;";
       return jdbcTemplate.queryForObject(sql,Integer.class, idTovar);
    }    

    @Override
    public void upravTovar(Tovar tovar) {
        String sql = "UPDATE tovar SET nazov=?, id_kategoria=?, id_znacka=?, cena=?, popis=?, obrazok_url=?, pocet_kusov=? "
                + "WHERE id = ?;";
        jdbcTemplate.update(sql, tovar.getNazov(), tovar.getIdKategoria(),  
                tovar.getIdZnacka(), tovar.getCena(), tovar.getPopis(), tovar.getObrazokUrl(), tovar.getPocetKusov(),tovar.getId());

    }
}
