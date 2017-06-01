package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.utils.StringUtilities;
import sk.upjs.ics.obchod.dao.ICategoryDao;
import sk.upjs.ics.obchod.dao.IProductDao;

public class CategoryManager implements ICategoryManager {

    private final ICategoryDao categoryDao;
    private final IProductDao productDao;

    public CategoryManager(ICategoryDao categoryDao, IProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public void update(Category category, String name) {
        String formattedName = StringUtilities.formatString(name);
        category.setName(formattedName);
        categoryDao.saveOrUpdate(category);
    }

    @Override
    public void save(Category category) {
        String formattedName = StringUtilities.formatString(category.getName());
        category.setName(formattedName);
        categoryDao.saveOrUpdate(category);
    }
    
    @Override
    public boolean categoryExists(String name) {
        String formattedName = StringUtilities.formatString(name);
        return categoryDao.findByName(formattedName) != null;
    }
    
    @Override
    public boolean productsOfCategoryExists(Category category) {        
        return !productDao.findByCategory(category).isEmpty();
    }
}
