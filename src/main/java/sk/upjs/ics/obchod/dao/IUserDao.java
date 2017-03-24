package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.User;

public interface IUserDao extends GenericDao<User> {
        
    User findById(Long id);
    
    User findByName(String name);    
    
    void updateLastLogin(User user);
    
}
