package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IBillDao;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.entity.Category;

public class MysqlBillDao extends MysqlEntityDao<Bill> implements IBillDao {

    public MysqlBillDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, "Bill", BeanPropertyRowMapper.newInstance(Bill.class));
    }

    @Override
    public List<Bill> getBillsForLastDay() {
        String sql = "SELECT * FROM Bill WHERE purchase_date BETWEEN CURDATE() AND NOW()";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Bill> getBillsForLastWeek() {
        String sql = "SELECT * FROM Bill WHERE purchase_date BETWEEN CURDATE() - INTERVAL 1 WEEK AND NOW()";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Bill> getBillsForLastMonth() {
        String sql = "SELECT * FROM Bill WHERE purchase_date BETWEEN CURDATE() - INTERVAL 1 MONTH AND NOW()";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Bill> getBillsForLastYear() {
        String sql = "SELECT * FROM Bill WHERE purchase_date BETWEEN CURDATE() - INTERVAL 1 YEAR AND NOW()";
        return jdbcTemplate.query(sql, rowMapper);
    }
    
    @Override
    public void addProductToBill(Product product, Bill bill, int quantity) {
        String sql = "INSERT INTO Bill_product(bill_id, product_name, category_name, "
                + "brand_name, quantity, price) VALUES(?,?,?,?,?,?)";
        
        jdbcTemplate.update(sql, bill.getId(), product.getName(), product.getCategory().getName(), 
                product.getBrand().getName(), quantity, product.getPrice() * quantity);
    }

    @Override
    public List<Product> getBillProducts(Bill bill) {
        String sql = "SELECT * FROM Bill_product WHERE bill_id = ?";

        return jdbcTemplate.query(sql, billProductRowMapper(), bill.getId());
    }

    @Override
    public List<Bill> getBillsForLastDays(int daysCount) {
        String sql = "SELECT * FROM Bill WHERE purchase_date BETWEEN CURDATE() - INTERVAL ? DAY AND NOW()";
        return jdbcTemplate.query(sql, rowMapper, daysCount);
    }
    
    @Override
    public void saveOrUpdate(Bill bill) {
        entityFields.put("id", bill.getId());
        entityFields.put("account_id", bill.getAccountId());
        entityFields.put("total_price", bill.getTotalPrice());
        entityFields.put("purchase_date", bill.getPurchaseDate());
        super.saveOrUpdate(bill);
    }
    
    private static RowMapper<Product> billProductRowMapper() {
        return (rs, i) -> {
            Category category = new Category(null, 
                rs.getString("category_name"));       
        
            Brand brand = new Brand(null, 
                rs.getString("brand_name"));
               
            Product billProduct = new Product(null, 
                rs.getString("product_name"), 
                brand, 
                category, 
                rs.getInt("price"), 
                rs.getInt("quantity"));
        
        return billProduct;
        };
    }

}
