package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.entity.Category;

public interface ICategoryManager {
    
    void update(Category category, String name);
    
    void save(Category category);
    
    boolean categoryExists(String name);
    
    boolean productsOfCategoryExists(Category category);
}
