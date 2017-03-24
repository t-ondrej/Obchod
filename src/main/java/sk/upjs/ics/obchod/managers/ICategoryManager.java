package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Category;

public interface ICategoryManager {
    
    List<Category> getCategories();
    
    void delete(Category category);
    
    void update(Category category, String name);
    
    Long add(Category category);
    
    boolean categoryExists(String name);
    
    boolean productsOfCategoryExists(Category category);
}
