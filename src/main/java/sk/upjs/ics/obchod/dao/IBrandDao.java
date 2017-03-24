
package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Brand;

public interface IBrandDao extends GenericDao<Brand> {
      
    Brand findById(Long id);
    
    Brand findByName(String name);
      
}
