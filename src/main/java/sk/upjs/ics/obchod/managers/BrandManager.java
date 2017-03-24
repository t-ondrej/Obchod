package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.utils.StringUtilities;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.IBrandDao;

public class BrandManager implements IBrandManager {

    private final IBrandDao brandDao;
    private final IProductDao productDao;

    public BrandManager(IBrandDao znackaDao, IProductDao tovarDao) {
        this.brandDao = znackaDao;
        this.productDao = tovarDao;
    }

    @Override
    public List<Brand> getBrands() {
        return brandDao.getAll();
    }

    @Override
    public void remove(Brand brand) {
        brandDao.delete(brand);
    }
    
    @Override
    public void update(Brand brand, String name) {
        String formattedName = StringUtilities.formatString(name);
        brand.setName(formattedName);
        brandDao.saveOrUpdate(brand);
    }
    
    @Override
    public Long save(Brand brand) {
        String formattedName = StringUtilities.formatString(brand.getName());
        brand.setName(formattedName);
        return brandDao.saveOrUpdate(brand);
    }

    @Override
    public boolean brandExists(String name) {
        String formattedName = StringUtilities.formatString(name);
        return brandDao.findByName(formattedName) != null;
    }
    
    @Override
    public boolean productOfBrandExists(Brand brand) {
        return !productDao.findProductsByBrand(brand).isEmpty();
    }

}
