package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.rowmappers.BillProductRowMapper;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IBillDao;

public class MysqlBillDao implements IBillDao {

    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Bill> mapper;

    public MysqlBillDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = BeanPropertyRowMapper.newInstance(Bill.class);
    }

    @Override
    public List<Bill> getAll() {
        String sql = "SELECT * FROM Faktura";

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Bill> getBillsForLastDay() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Bill> getBillsForLastWeek() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 WEEK AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Bill> getBillsForLastMonth() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 MONTH AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Bill> getBillsForLastYear() {
        String sql = "SELECT * FROM Faktura WHERE datum_nakupu BETWEEN CURDATE() - INTERVAL 1 YEAR AND NOW()";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Long saveOrUpdate(Bill bill) {
        String sql = "INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu) VALUES(?,?,?)";

        Long idFaktura = -1L;

        try {
            Connection conn = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stm.setLong(1, bill.getUserId());
            stm.setInt(2, bill.getTotalPrice());
            stm.setObject(3, bill.getPurchaseDate());
            stm.execute();

            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                idFaktura = rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return idFaktura;
    }

    @Override
    public void delete(Bill bill) {
        String sql = "DELETE FROM Faktura WHERE id = ?";

        jdbcTemplate.update(sql, bill.getId());
    }

    @Override
    public void addProductToBill(Product product, Bill bill, int quantity) {
        String sql = "INSERT INTO Tovar_Faktury(id_faktura, nazov_tovaru, nazov_kategorie, "
                + "nazov_znacky, pocet_kusov_tovaru, cena) VALUES(?,?,?,?,?,?)";
        
        jdbcTemplate.update(sql, bill.getId(), product.getName(), product.getCategory().getName(), 
                product.getBrand().getName(), quantity, product.getPrice() * quantity);
    }

    @Override
    public List<Product> getBillProducts(Bill bill) {
        String sql = "SELECT * FROM tovar_faktury WHERE id_faktura = ?";

        return jdbcTemplate.query(sql, new BillProductRowMapper(), bill.getId());
    }

}
