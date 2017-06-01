package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.dao.IProductDao;

public class MysqlProductDao extends MysqlEntityDao<Product>implements IProductDao {

    private final String getProductsSql = "SELECT P.id AS product_id, P.`name` AS product_name, "
            + "P.category_id AS category_id, P.brand_id AS brand_id, "
            + "P.price AS price, P.description AS description, P.image_path AS image_path, "
            + "P.quantity AS quantity, C.`name` AS category_name, B.`name` AS brand_name "
            + "FROM Product P JOIN Category C ON P.category_id = C.id "
            + "JOIN Brand B ON P.brand_id = B.id ";

    public MysqlProductDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Product", productRowMapper());
    }

    @Override
    public List<Product> getAll() {
        return jdbcTemplate.query(getProductsSql, rowMapper);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        String sql = getProductsSql + "WHERE category_id = ?";

        return jdbcTemplate.query(sql, rowMapper, category.getId());
    }

    @Override
    public Product findByName(String name) {
        String sql = getProductsSql + "WHERE P.`name` = ?";

        try {
            return (Product) jdbcTemplate.queryForObject(sql, rowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }     
    }

    @Override
    public List<Product> findByBrand(Brand brand) {
        String sql = getProductsSql + "WHERE brand_id = ?";

        return jdbcTemplate.query(sql, rowMapper, brand.getId());
    }

    @Override
    public void saveOrUpdate(Product product) {
        entityFields.put("id", product.getId());
        entityFields.put("`name`", product.getName());
        entityFields.put("category_id", product.getCategory().getId());
        entityFields.put("brand_id", product.getBrand().getId());
        entityFields.put("price", product.getPrice());
        entityFields.put("description", product.getDescription());
        entityFields.put("image_path", product.getImagePath());
        entityFields.put("quantity", product.getQuantity());
        super.saveOrUpdate(product);
    }

    @Override
    public Product findById(Long id) {
        String sql = getProductsSql + "WHERE P.id = ?";
        
        try {
            return (Product) jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }      
    }
    
    @Override
    public void setProductQuantity(Product product, int quantity) {
        String sql = "UPDATE Product SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, product.getId());

    }

    @Override
    public int getProductQuantity(Product product) {
        String sql = "SELECT quantity FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, int.class, product.getId());
    }
    
    private static RowMapper<Product> productRowMapper() {
        return (rs, i) -> {
            Category category = new Category(rs.getLong("category_id"), 
                rs.getString("category_name"));
        
            Brand brand = new Brand(rs.getLong("brand_id"), 
                rs.getString("brand_name"));
        
            Product product = new Product(rs.getLong("product_id"), 
                rs.getString("product_name"),
                brand,
                category,
                rs.getInt("price"),
                rs.getString("description"),
                rs.getString("image_path"),
                rs.getInt("quantity"));
  
            return product;
        };
    }
}
