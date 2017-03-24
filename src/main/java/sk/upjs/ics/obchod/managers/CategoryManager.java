package sk.upjs.ics.obchod.managers;

import java.util.List;
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
    public List<Category> getCategories() {
        return categoryDao.getAll();
    }

    @Override
    public void delete(Category category) {
        categoryDao.delete(category);
    }

    @Override
    public boolean categoryExists(String name) {
        String formattedName = StringUtilities.formatString(name);
        return categoryDao.findByName(formattedName) != null;
    }

    @Override
    public void update(Category category, String name) {
        String formattedName = StringUtilities.formatString(name);
        category.setName(formattedName);
        categoryDao.saveOrUpdate(category);
    }

    @Override
    public Long add(Category category) {
        String formattedName = StringUtilities.formatString(category.getName());
        category.setName(formattedName);
        return categoryDao.saveOrUpdate(category);
    }
    
    @Override
    public boolean productsOfCategoryExists(Category category) {        
        return !productDao.findProductsByCategory(category).isEmpty();
    }

}
