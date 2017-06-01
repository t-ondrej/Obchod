
package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Category;

public interface ICategoryDao extends IEntityDao<Category>{  
       
    Category findByName(String name);     
}
