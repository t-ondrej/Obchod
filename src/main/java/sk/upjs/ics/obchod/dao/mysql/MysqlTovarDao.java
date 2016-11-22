package sk.upjs.ics.obchod.dao.mysql;

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
    public List<Tovar> dajTovar() {
        String sql = "SELECT * FROM Tovar";
                    
        return jdbcTemplate.query(sql, mapper);
    }
    
      @Override
    public List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria) {
        String sql = "SELECT * FROM Tovar WHERE id_kategoria = ?";
        
        return jdbcTemplate.query(sql, mapper, kategoria.getId());
    }

    @Override
    public List<Tovar> dajTovarPodlaNazvu(String nazov) {
        String sql = "SELECT * FROM Tovar WHERE nazov = ?";
        
        return jdbcTemplate.query(sql, mapper, nazov);
    }

    @Override
    public List<Tovar> dajTovarPodlaZnacky(Znacka znacka) {
        String sql = "SELECT * FROM Tovar WHERE id_znacka = ?";
        
        return jdbcTemplate.query(sql, mapper, znacka.getId());
    }   

    @Override
    public void pridajTovar(Tovar tovar) {
        String sql = "INSERT INTO Tovar (id_kategoria, nazov, id_znacka, cena, popis, obrazok_url, pocet_kusov) VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, tovar.getIdKategoria(), tovar.getNazov(), 
                tovar.getIdZnacka(), tovar.getCena(), tovar.getPopis(), tovar.getobrazokUrl(), tovar.getPocet_kusov());
    }

    @Override
    public void odstranTovar(Tovar tovar) {
        String sql = "DELETE FROM Tovar WHERE id = ?";
        
        jdbcTemplate.update(sql, tovar.getId());
    }

    @Override
    public Tovar najdiPodlaId(Long id) {
        String sql = "SELECT * FROM tovar WHERE id = ?";        
        BeanPropertyRowMapper<Tovar> mapper = BeanPropertyRowMapper.newInstance(Tovar.class);        
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public void nastavTovaruPocetKusov(Tovar tovar, int pocet) {       
       
       String sql = "UPDATE Tovar SET pocet_kusov = ? WHERE id = ?"; 
       jdbcTemplate.update(sql, pocet, tovar.getId());       
       
    }

    
}
