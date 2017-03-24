package sk.upjs.ics.obchod.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.rowmappers.ProductRowMapper;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IProductDao;

public class MysqlProductDao implements IProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final ProductRowMapper mapper = new ProductRowMapper();

    private final String getProductsSql = "SELECT T.id AS id_tovar, T.nazov AS nazov_tovar, "
            + "T.id_kategoria AS id_kategoria, T.id_znacka AS id_znacka, "
            + "T.cena AS cena, T.popis AS popis, T.obrazok_url AS obrazok_url, "
            + "T.pocet_kusov AS pocet_kusov, K.nazov AS nazov_kategoria, Z.nazov AS nazov_znacka "
            + "FROM Tovar T JOIN Kategoria K ON T.id_kategoria = K.id "
            + "JOIN Znacka Z ON T.id_znacka = Z.id ";

    public MysqlProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query(getProductsSql, mapper);
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {
        String sql = getProductsSql + "WHERE id_kategoria = ?";

        return jdbcTemplate.query(sql, mapper, category.getId());
    }

    @Override
    public Product findProductsByName(String name) {
        String sql = getProductsSql + "WHERE T.nazov = ?";

        return (Product) jdbcTemplate.queryForObject(sql, mapper, name);
    }

    @Override
    public List<Product> findProductsByBrand(Brand brand) {
        String sql = getProductsSql + "WHERE id_znacka = ?";

        return jdbcTemplate.query(sql, mapper, brand.getId());
    }

    @Override
    public Long saveOrUpdate(Product product) {

        if (product.getId() == 0) {
            String sql = "INSERT INTO Tovar ( nazov, id_kategoria, id_znacka, cena, popis, obrazok_url, pocet_kusov) VALUES(?, ?, ?, ?, ?, ?, ?)";

            Long idTovar = -1L;

            try {
                Connection conn = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement stm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stm.setString(1, product.getName());
                stm.setLong(2, product.getCategory().getId());
                stm.setLong(3, product.getBrand().getId());
                stm.setInt(4, product.getPrice());
                stm.setString(5, product.getDescription());
                stm.setString(6, product.getImagePath());
                stm.setInt(7, product.getQuantity());
                stm.execute();

                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idTovar = rs.getLong(1);
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return idTovar;

        } else {
            String sql = "UPDATE tovar SET nazov = ?, id_kategoria = ?, id_znacka = ?, c"
                    + "ena = ?, popis = ?, obrazok_url = ?, pocet_kusov = ? "
                    + "WHERE id = ?;";
            jdbcTemplate.update(sql, product.getName(), product.getCategory().getId(),
                    product.getBrand().getId(), product.getPrice(), product.getDescription(), 
                    product.getImagePath(), product.getQuantity(), product.getId());

            return product.getId();
        }
    }

    @Override
    public void delete(Product product) {
        String sql = "DELETE FROM Tovar WHERE id = ?";

        jdbcTemplate.update(sql, product.getId());
    }

    @Override
    public Product findById(Long id) {
        String sql = getProductsSql + "WHERE T.id = ?";
        return (Product) jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public void setProductQuantity(Product product, int quantity) {

        String sql = "UPDATE Tovar SET pocet_kusov = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, product.getId());

    }

    @Override
    public int getProductQuantity(Product product) {
        String sql = "SELECT pocet_kusov FROM tovar WHERE id = ?;";
        return jdbcTemplate.queryForObject(sql, Integer.class, product.getId());
    }
}
