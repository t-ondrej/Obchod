package sk.upjs.ics.obchod.dao.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public class BillProductRowMapper  implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Product billProduct = new Product();
        billProduct.setName(rs.getString("nazov_tovaru"));
        
        Category category = new Category();
        category.setName(rs.getString("nazov_kategorie"));
        billProduct.setCategory(category);
        
        Brand brand = new Brand();
        brand.setName(rs.getString("nazov_znacky"));
        billProduct.setBrand(brand);
        
        billProduct.setPrice(rs.getInt("cena"));
        billProduct.setQuantity(rs.getInt("pocet_kusov_tovaru"));
        
        return billProduct;
    }
    
}
