package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.dao.mysql.MysqlFakturaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlKategoriaDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlPouzivatelDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlTovarDao;
import sk.upjs.ics.obchod.dao.mysql.MysqlZnackaDao;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum TestDaoFactory {
    INSTANCE;
    
    private MysqlPouzivatelDao mysqlPouzivatelDao;
    
    private MysqlTovarDao mysqlTovarDao;
    
    private MysqlKategoriaDao mysqlKategoriaDao;
    
    private MysqlZnackaDao mysqlZnackaDao;
    
    private PamatoviKosikDao pamatoviKosikDao;
    
    private MysqlFakturaDao mysqlFakturaDao;
    
    private JdbcTemplate jdbcTemplate;
    
    private TestDaoFactory() {
        jdbcTemplate = new JdbcTemplate(getDataSource());
    }
    
    private MysqlDataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/Obchod_test?serverTimezone=UTC");
        dataSource.setUser("obchod_test");
        dataSource.setPassword("obchod1_test");
        
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
    
    public MysqlZnackaDao getMysqlZnackaDao() {
        if (mysqlZnackaDao == null)
            mysqlZnackaDao = new MysqlZnackaDao(jdbcTemplate);
        
        return mysqlZnackaDao;
    }
    
    public PamatoviKosikDao getPamatoviKosikDao() {
        if (pamatoviKosikDao == null)
            pamatoviKosikDao = new PamatoviKosikDao();
        
        return pamatoviKosikDao;
    }
    
    public MysqlFakturaDao getMysqlFakturaDao() {
        if (mysqlFakturaDao == null)
            mysqlFakturaDao = new MysqlFakturaDao(jdbcTemplate);
        
        return mysqlFakturaDao;
    }
    
    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
