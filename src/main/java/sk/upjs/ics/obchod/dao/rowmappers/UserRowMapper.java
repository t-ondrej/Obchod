package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        Cart cart = new Cart();        
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("prihlasovacie_meno"));
        user.setName(rs.getString("meno"));
        user.setSurname(rs.getString("priezvisko"));
        user.setCity(rs.getString("mesto"));
        user.setStreet(rs.getString("ulica"));
        user.setPostalCode(rs.getInt("psc"));
        user.setPasswordHash(rs.getString("hash_hesla"));
        user.setSalt(rs.getString("sol"));
        user.setEmail(rs.getString("email"));
        LocalTime time = LocalTime.now();
        user.setLastLogin(LocalDateTime.of(rs.getDate("posledne_prihlasenie").toLocalDate(), time));
        user.setIsAdmin(rs.getBoolean("je_administrator"));
        user.setCart(cart);
        return user;
    }
    
}
