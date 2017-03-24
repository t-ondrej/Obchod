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
import sk.upjs.ics.obchod.dao.rowmappers.UserRowMapper;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.dao.IUserDao;

public class MysqlUserDao implements IUserDao {

    private final JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM Pouzivatel";

        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User findByName(String name) {
        String sql = "SELECT * FROM Pouzivatel WHERE prihlasovacie_meno = ?";
        User user;

        try {
            user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), name);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        return user;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM Pouzivatel WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }

    @Override
    public Long saveOrUpdate(User user) {

        if (user.getId() == 0) {
            String sql = "INSERT INTO Pouzivatel (prihlasovacie_meno, meno, priezvisko, mesto, ulica, psc, hash_hesla, sol, email, posledne_prihlasenie, je_administrator) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Long idPouzivatel = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, user.getLogin());
                stm.setString(2, user.getName());
                stm.setString(3, user.getSurname());
                stm.setString(4, user.getCity());
                stm.setString(5, user.getStreet());
                stm.setInt(6, user.getPostalCode());
                stm.setString(7, user.getPasswordHash());
                stm.setString(8, user.getSalt());
                stm.setString(9, user.getEmail());
                stm.setObject(10, user.getLastLogin());
                stm.setBoolean(11, user.isAdministrator());
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
            
            jdbcTemplate.update(sql, user.getLogin(), user.getName(),
                    user.getSurname(), user.getCity(), user.getStreet(),
                    user.getPostalCode(), user.getPasswordHash(), user.getSalt(),
                    user.getEmail(), user.getLastLogin(),
                    user.isAdministrator(), user.getId());

            return user.getId();
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM Pouzivatel WHERE id = ?";

        jdbcTemplate.update(sql, user.getId());
    }

    @Override
    public void updateLastLogin(User user) {
        String sql = "UPDATE Pouzivatel SET posledne_prihlasenie = ? WHERE id = ?;";
        jdbcTemplate.update(sql, LocalDateTime.now(), user.getId());
    }

}
