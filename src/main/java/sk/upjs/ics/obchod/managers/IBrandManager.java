package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.entity.Brand;

public interface IBrandManager {
    
    void update(Brand brand, String name);
    
    void save(Brand brand);
    
    boolean brandExists(String name);
    
    boolean productOfBrandExists(Brand brand);
}
