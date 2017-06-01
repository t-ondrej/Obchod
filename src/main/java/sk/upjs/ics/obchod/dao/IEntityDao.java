package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Entity;

public interface IEntityDao<T extends Entity> {
    
    List<T> getAll();
    
    void saveOrUpdate(T entity);
    
    void delete(T entity);
    
    T findById(Long id);
    
}
