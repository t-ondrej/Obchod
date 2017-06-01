package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.dao.DaoFactory;

public enum EntityManagerFactory {
    INSTANCE;
    
    private IBillManager billManager;
    
    private ICategoryManager categoryManager;   
    
    private IPersonManager personManager;
    
    private IBrandManager brandManager;
    
    private IAccountManager accountManager;
    
    private final DaoFactory daoFactory = DaoFactory.INSTANCE;
    
    public IBillManager getBillManager() {
        if (billManager == null)
            this.billManager =  new BillManager(daoFactory.getMysqlBillDao());
        
        return billManager;
    }
    
    public ICategoryManager getCategoryManager() {
        if (categoryManager == null)
            this.categoryManager =  new CategoryManager(daoFactory.getMysqlCategoryDao(), 
                    daoFactory.getMysqlProductDao());
        
        return categoryManager;
    }
    
    public IPersonManager getPersonManager() {
        if (personManager == null)
            this.personManager =  new PersonManager(daoFactory.getMysqlPersonDao());
        
        return personManager;
    }
    
    public IBrandManager getBrandManager() {
        if (brandManager == null)
            this.brandManager =  new BrandManager(daoFactory.getMysqlBrandDao(), 
                    daoFactory.getMysqlProductDao());
        
        return brandManager;
    }
    
    public IAccountManager getAccountManager() {
        if (accountManager == null)
            this.accountManager =  new AccountManager(daoFactory.getMysqlAccountDao());
        
        return accountManager;
    }
            
}
