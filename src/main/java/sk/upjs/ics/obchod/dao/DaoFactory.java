package sk.upjs.ics.obchod.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;
    
    private MysqlPouzivatelDao mysqlPouzivatelDao;
    
    private MysqlTovarDao mysqlTovarDao;
    
    private MysqlKategoriaDao mysqlKategoriaDao;
    
    private JdbcTemplate jdbcTemplate;
    
    private DaoFactory() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
    }
    
    private MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/Obchod?serverTimezone=UTC");
        dataSource.setUser("obchod");
        dataSource.setPassword("obchod1");
        
        return dataSource;
    }
    
    public MysqlTovarDao getMysqlTovarDao() {
        if (mysqlTovarDao == null)
            mysqlTovarDao = new MysqlTovarDao(jdbcTemplate);
        
        return mysqlTovarDao;
    }
    
    public MysqlPouzivatelDao getMysqlPouzivatelDao() {
        if (mysqlPouzivatelDao == null)
            mysqlPouzivatelDao = new MysqlPouzivatelDao(jdbcTemplate);
        
        return mysqlPouzivatelDao;
    }
    
    public MysqlKategoriaDao getMysqlKategoriaDao() {
        if (mysqlKategoriaDao == null)
            mysqlKategoriaDao = new MysqlKategoriaDao(jdbcTemplate);
        
        return mysqlKategoriaDao;
    }
}
