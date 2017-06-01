package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.dao.mysql.MysqlBillDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlCategoryDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlPersonDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlBrandDao;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.mysql.MysqlAccountDao;

public enum DaoFactory {
    INSTANCE;
    
    private IPersonDao mysqlPersonDao;
    
    private IAccountDao mysqlAccountDao;
    
    private IProductDao mysqlProductDao;
    
    private ICategoryDao mysqlCategoryDao;
    
    private IBrandDao mysqlBrandDao; 
    
    private IBillDao mysqlBillDao;
    
    private JdbcTemplate jdbcTemplate;
    
    private DaoFactory() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getProductionTemplate();
    }
    
    
    public IPersonDao getMysqlPersonDao() {
        if (mysqlPersonDao == null)
            mysqlPersonDao = new MysqlPersonDao(jdbcTemplate);
        
        return mysqlPersonDao;
    }
    
    public IAccountDao getMysqlAccountDao() {
        if (mysqlAccountDao == null)
            mysqlAccountDao = new MysqlAccountDao(jdbcTemplate);
        
        return mysqlAccountDao;
    }
    
    public IProductDao getMysqlProductDao() {
        if (mysqlProductDao == null)
            mysqlProductDao = new MysqlProductDao(jdbcTemplate);
        
        return mysqlProductDao;
    }
    
    public ICategoryDao getMysqlCategoryDao() {
        if (mysqlCategoryDao == null)
            mysqlCategoryDao = new MysqlCategoryDao(jdbcTemplate);
        
        return mysqlCategoryDao;
    }
    
    public IBrandDao getMysqlBrandDao() {
        if (mysqlBrandDao == null)
            mysqlBrandDao = new MysqlBrandDao(jdbcTemplate);
        
        return mysqlBrandDao;
    }
    
    public IBillDao getMysqlBillDao() {
        if (mysqlBillDao == null)
            mysqlBillDao = new MysqlBillDao(jdbcTemplate);
        
        return mysqlBillDao;
    }
}
