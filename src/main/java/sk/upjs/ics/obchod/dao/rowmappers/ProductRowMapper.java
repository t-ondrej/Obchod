package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public class ProductRowMapper implements RowMapper {
    
    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id_tovar"));
        product.setName(rs.getString("nazov_tovar"));
        
        Category category = new Category();
        category.setId(rs.getLong("id_kategoria"));
        category.setName(rs.getString("nazov_kategoria"));
        product.setCategory(category);
        
        Brand brand = new Brand();
        brand.setId(rs.getLong("id_znacka"));
        brand.setName(rs.getString("nazov_znacka"));
        product.setBrand(brand);
        
        product.setPrice(rs.getInt("cena"));
        product.setDescription(rs.getString("popis"));
        product.setImagePath(rs.getString("obrazok_url"));
        product.setQuantity(rs.getInt("pocet_kusov"));
        
        return product;
    }
    
}
