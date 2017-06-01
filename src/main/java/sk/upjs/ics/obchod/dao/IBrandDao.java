
package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Brand;

public interface IBrandDao extends IEntityDao<Brand> {
        
    Brand findByName(String name);      
}
