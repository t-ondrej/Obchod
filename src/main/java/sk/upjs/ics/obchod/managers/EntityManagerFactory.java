package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.dao.DaoFactory;

public enum EntityManagerFactory {
    INSTANCE;
    
    private IFakturaManager fakturaManager;
    
    private IKategoriaManager kategoriaManager;
    
    private IKosikManager kosikManager;
    
    private IPouzivatelManager pouzivatelManager;
    
    private IZnackaManager znackaManager;
    
    private final DaoFactory daoFactory = DaoFactory.INSTANCE;
    
    public IFakturaManager getFakturaManager() {
        if (fakturaManager == null)
            return new FakturaManager(daoFactory.getMysqlFakturaDao(), 
                    daoFactory.getPamatoviKosikDao());
        
        return fakturaManager;
    }
    
    public IKategoriaManager getKategoriaManager() {
        if (kategoriaManager == null)
            return new KategoriaManager(daoFactory.getMysqlKategoriaDao(), 
                    daoFactory.getMysqlTovarDao());
        
        return kategoriaManager;
    }
    
    public IKosikManager getKosikManager() {
        if (kosikManager == null)
            return new KosikManager(daoFactory.getPamatoviKosikDao(), 
                    daoFactory.getMysqlTovarDao(), pouzivatelManager);
        
        return kosikManager;
    }
    
    public IPouzivatelManager getPouzivatelManager() {
        if (pouzivatelManager == null)
            return new PouzivatelManager(daoFactory.getMysqlPouzivatelDao());
        
        return pouzivatelManager;
    }
    
    public IZnackaManager getZnackaManager() {
        if (znackaManager == null)
            return new ZnackaManager(daoFactory.getMysqlZnackaDao(), 
                    daoFactory.getMysqlTovarDao());
        
        return znackaManager;
    }
            
}
