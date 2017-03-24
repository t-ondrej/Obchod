package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Brand;

public interface IBrandManager {

    List<Brand> getBrands();

    void remove(Brand brand);
    
    void update(Brand brand, String name);
    
    Long save(Brand brand);
    
    boolean brandExists(String name);
    
    boolean productOfBrandExists(Brand brand);
}
