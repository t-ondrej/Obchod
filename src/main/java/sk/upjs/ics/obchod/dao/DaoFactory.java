package sk.upjs.ics.obchod.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;
    
    private MysqlPouzivatelDao mysqlPouzivatelDao;
    
    private MysqlTovarDao mysqlTovarDao;
    
    private JdbcTemplate jdbcTemplate;
    
    private DaoFactory() {
        jdbcTemplate = new JdbcTemplate(createDataSource());
    }
    
    private MysqlDataSource createDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/Obchod");
        dataSource.setUser("Obchod");
        dataSource.setPassword("obchod1");
        
        return dataSource;
    }
    
    public MysqlTovarDao getMysqlTovarDao(JdbcTemplate jdbcTemplate) {
        if (mysqlTovarDao == null)
            mysqlTovarDao = new MysqlTovarDao(jdbcTemplate);
        
        return mysqlTovarDao;
    }
    
    public MysqlPouzivatelDao getMysqlPouzivatelDao(JdbcTemplate jdbcTemplate) {
        if (mysqlPouzivatelDao == null)
            mysqlPouzivatelDao = new MysqlPouzivatelDao(jdbcTemplate);
        
        return mysqlPouzivatelDao;
    }
}
