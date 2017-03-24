
package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Category;

public interface ICategoryDao extends GenericDao<Category>{  
    
    Category finById(Long id);
    
    Category findByName(String name);     
}
