package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;

public interface IProductDao extends IEntityDao<Product> {
        
    Product findByName(String name);
    
    List<Product> findByCategory(Category category);  
    
    List<Product> findByBrand(Brand brand);
      
    int getProductQuantity(Product product);
    
    void setProductQuantity(Product product, int quantity);               
}
