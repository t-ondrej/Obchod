package sk.upjs.ics.obchod.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum JdbcTemplateFactory {
    INSTANCE;
    
    private JdbcTemplate testTemplate;
    
    private JdbcTemplate productionTemplate;
    
    public JdbcTemplate getTestTemplate() {
        if (testTemplate == null)
            testTemplate =  new JdbcTemplate(createDataSource(true));
             
        return testTemplate;
    }
    
    public JdbcTemplate getProductionTemplate() {
        if (productionTemplate == null)
            productionTemplate =  new JdbcTemplate(createDataSource(false));
             
        return productionTemplate;
    }
    
    private MysqlDataSource createDataSource(boolean forTests) {
        MysqlDataSource dataSource = new MysqlDataSource();
        
        String testDatabaseUrl = "jdbc:mysql://localhost:3306/shop_test?serverTimezone=UTC";
        String productionDatabaseUrl = "jdbc:mysql://localhost:3306/Obchod?serverTimezone=UTC";
        
        dataSource.setUrl(forTests ? testDatabaseUrl : productionDatabaseUrl);
        dataSource.setUser(forTests ? "testing_user" : "obchod");
        dataSource.setPassword(forTests ? "qwerty" : "obchod1");
        
        return dataSource;
    }
    
    
}
