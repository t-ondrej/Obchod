package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public interface IProductDao extends GenericDao<Product> {
        
    List<Product> findProductsByCategory(Category category);
    
    Product findProductsByName(String name);
    
    List<Product> findProductsByBrand(Brand brand);
    
    Product findById(Long id);
    
    int getProductQuantity(Product product);
    
    void setProductQuantity(Product product, int quantity);               
}
