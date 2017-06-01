package sk.upjs.ics.obchod.utils;

import java.sql.SQLException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;

public class TestDataProvider {

    private static final JdbcTemplate jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
    
    private static final String insertTestDataScript = "sql\\insert_test_data.sql";
    
    private static final String clearTestDataScript = "sql\\truncate_all_tables.sql";
    
    private TestDataProvider() {
        
    }
    
    public static void insertTestData() {
        FileSystemResource fsr = new FileSystemResource(insertTestDataScript);
        
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), fsr);
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }   
        
    public static void clearTestData() {
        FileSystemResource fsr = new FileSystemResource(clearTestDataScript);
        
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), fsr);
        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }
}
