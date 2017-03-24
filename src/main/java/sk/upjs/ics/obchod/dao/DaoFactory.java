package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.dao.mysql.MysqlBillDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlCategoryDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlUserDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlBrandDao;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;
    
    private MysqlUserDao mysqlPouzivatelDao;
    
    private MysqlProductDao mysqlProductDao;
    
    private MysqlCategoryDao mysqlCategoryDao;
    
    private MysqlBrandDao mysqlBrandDao;
    
    private MemoryCartDao memoryCartDao;
    
    private MysqlBillDao mysqlBillDao;
    
    private JdbcTemplate jdbcTemplate;
    
    private DaoFactory() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getProductionTemplate();
    }
    
    public MysqlProductDao getMysqlProductDao() {
        if (mysqlProductDao == null)
            mysqlProductDao = new MysqlProductDao(jdbcTemplate);
        
        return mysqlProductDao;
    }
    
    public MysqlUserDao getMysqlUserDao() {
        if (mysqlPouzivatelDao == null)
            mysqlPouzivatelDao = new MysqlUserDao(jdbcTemplate);
        
        return mysqlPouzivatelDao;
    }
    
    public MysqlCategoryDao getMysqlCategoryDao() {
        if (mysqlCategoryDao == null)
            mysqlCategoryDao = new MysqlCategoryDao(jdbcTemplate);
        
        return mysqlCategoryDao;
    }
    
    public MysqlBrandDao getMysqlBrandDao() {
        if (mysqlBrandDao == null)
            mysqlBrandDao = new MysqlBrandDao(jdbcTemplate);
        
        return mysqlBrandDao;
    }
    
    public MemoryCartDao getMemoryCartDao() {
        if (memoryCartDao == null)
            memoryCartDao = new MemoryCartDao();
        
        return memoryCartDao;
    }
    
    public MysqlBillDao getMysqlBillDao() {
        if (mysqlBillDao == null)
            mysqlBillDao = new MysqlBillDao(jdbcTemplate);
        
        return mysqlBillDao;
    }
    
    private JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
