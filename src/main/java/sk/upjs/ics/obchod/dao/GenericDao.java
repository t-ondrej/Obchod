package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Entity;

public interface GenericDao<T extends Entity> {
    
    List<T> getAll();
    
    Long saveOrUpdate(T entity);
    
    void delete(T entity);
}
