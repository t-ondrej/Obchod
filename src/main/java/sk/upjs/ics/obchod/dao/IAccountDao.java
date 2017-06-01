package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Account;

public interface IAccountDao extends IEntityDao<Account> {
    
    void updateLastLogin(Account account);
    
    public Account findByUsername(String username);
}
