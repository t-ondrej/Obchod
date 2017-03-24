package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.dao.DaoFactory;

public enum EntityManagerFactory {
    INSTANCE;
    
    private IBillManager billManager;
    
    private ICategoryManager categoryManager;
    
    private ICartManager cartManager;
    
    private IUserManager userManager;
    
    private IBrandManager brandManager;
    
    private final DaoFactory daoFactory = DaoFactory.INSTANCE;
    
    public IBillManager getBillManager() {
        if (billManager == null)
            return new BillManager(daoFactory.getMysqlBillDao(), 
                    daoFactory.getMemoryCartDao());
        
        return billManager;
    }
    
    public ICategoryManager getCategoryManager() {
        if (categoryManager == null)
            return new CategoryManager(daoFactory.getMysqlCategoryDao(), 
                    daoFactory.getMysqlProductDao());
        
        return categoryManager;
    }
    
    public ICartManager getCartManager() {
        if (cartManager == null)
            return new CartManager(daoFactory.getMemoryCartDao(), 
                    daoFactory.getMysqlProductDao(), userManager);
        
        return cartManager;
    }
    
    public IUserManager getUserManager() {
        if (userManager == null)
            return new UserManager(daoFactory.getMysqlUserDao());
        
        return userManager;
    }
    
    public IBrandManager getBrandManager() {
        if (brandManager == null)
            return new BrandManager(daoFactory.getMysqlBrandDao(), 
                    daoFactory.getMysqlProductDao());
        
        return brandManager;
    }
            
}
