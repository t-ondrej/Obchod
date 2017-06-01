package sk.upjs.ics.obchod.dao.mysql;

import java.util.List;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.entity.Category;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.entity.Brand;
import sk.upjs.ics.obchod.utils.TestDataProvider;

// DONE
public class MysqlProductDaoTest {

    private MysqlProductDao productDao;
    private JdbcTemplate jdbcTemplate;

    private final String vyberTovarSql = "SELECT P.id AS product_id, P.`name` AS product_name, "
            + "P.category_id AS category_id, P.brand_id AS brand_id, "
            + "P.price AS price, P.description AS description, P.image_path AS image_path, "
            + "P.quantity AS quantity, C.`name` AS category_name, B.`name` AS brand_name "
            + "FROM Product P JOIN Category C ON P.category_id = C.id "
            + "JOIN Brand B ON P.brand_id = B.id ";

    public MysqlProductDaoTest() {
        jdbcTemplate = JdbcTemplateFactory.INSTANCE.getTestTemplate();
        productDao = new MysqlProductDao(jdbcTemplate);
    }

    @BeforeClass
    public static void setUp() {
        TestDataProvider.insertTestData();
    }
    
    @AfterClass
    public static void tearDown() {
        TestDataProvider.clearTestData();
    }

    /**
     * Test of dajTovar method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovar() {
        System.out.println("dajTovar");        

        List<Product> tovar = productDao.getAll();
        Assert.assertNotNull(tovar);
    }

    /**
     * Test of dajTovarPodlaKategorie method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaKategorie() {
        System.out.println("dajTovarPodlaKategorie");        

        Category category1 = new Category(1L, "C1");
        Category category4 = new Category(3L, "C4");

        List<Product> category1Products = productDao.findByCategory(category1);
        List<Product> category4Products = productDao.findByCategory(category4);
        Assert.assertEquals(2, category1Products.size());
        Assert.assertEquals(0, category4Products.size());
    }

    /**
     * Test of dajTovarPodlaNazvu method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaNazvu() {
        System.out.println("dajTovarPodlaNazvu");        

        Product product = productDao.findByName("P1");
        Assert.assertEquals("P1", product.getName());
    }

    /**
     * Test of dajTovarPodlaZnacky method, of class MysqlTovarDao.
     */
    @Test
    public void testDajTovarPodlaZnacky() {
        System.out.println("dajTovarPodlaZnacky");        

        Brand znacka4 = new Brand(4L, "B4");
        Brand znacka1 = new Brand(1L, "B1");

        List<Product> brand4Products = productDao.findByBrand(znacka4);
        List<Product> brand1Products = productDao.findByBrand(znacka1);
        Assert.assertEquals(0, brand4Products.size());
        Assert.assertEquals(2, brand1Products.size());
    }

    @Test
    public void testUlozTovarPridaj() {
        System.out.println("ulozTovar");
        
        Category category1 = new Category(5L, "C5");
        Brand brand1 = new Brand(2L, "B2");
        
        Product product = new Product(null, "name1", brand1, category1, 42, 
                "description", "@../img/3.JPG", 6);


        productDao.saveOrUpdate(product);        
        Assert.assertNotNull(product.getId());
    }

    @Test
    public void testUlozTovarUprav() {
        System.out.println("ulozTovar");        

        Category category2 = new Category(2L, "C2");
        Brand brand1 = new Brand(3L, "B3");
        
        Product product = new Product(3L, "P11", brand1, category2, 0, "desc1", "@../img/3.JPG", 3);

        productDao.saveOrUpdate(product);
        String sql = vyberTovarSql + "WHERE P.id = 3";
        product = (Product) jdbcTemplate.queryForObject(sql, productDao.rowMapper);

        Assert.assertEquals(new Long(3), product.getId());
        Assert.assertEquals(0, product.getPrice());
        Assert.assertEquals("desc1", product.getDescription());
        Assert.assertEquals(3, product.getQuantity());
    }

    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");   
        
        Product product = new Product(5L, null, null, null, -1, null, null, -1);
        productDao.delete(product);
        
        String sql2 = "SELECT COUNT(*) FROM Product WHERE id = 5";
        Assert.assertEquals(new Long(0), jdbcTemplate.queryForObject(sql2, Long.class));
    }


    @Test
    public void testNajdiPodlaId() {
        System.out.println("najdiPodlaId");        

        Product t = productDao.findById(1L);
        Assert.assertEquals(new Long(1), t.getCategory().getId());
        Assert.assertEquals(new Long(1), t.getBrand().getId());
        Assert.assertEquals("P1", t.getName());
        Assert.assertEquals(1, t.getPrice());
        Assert.assertEquals("@../img/1.JPG", t.getImagePath());
        Assert.assertEquals("desc1", t.getDescription());
    }

    @Test
    public void testNastavTovaruPocetKusov() {
        System.out.println("nastavTovaruPocetKusov");        
        Product tovar = new Product(4L, null, null, null, -1, null, null, -1);

        productDao.setProductQuantity(tovar, 9);
        String sql = "SELECT quantity FROM Product WHERE id = 4";
        Assert.assertEquals(new Long(9), jdbcTemplate.queryForObject(sql, Long.class));
    }

    
    @Test
    public void testDajPocetTovaru() {
        System.out.println("dajPocetTovaru");  
        
        Product tovar = new Product(2L, null, null, null, -1, null, null, -1);
        Assert.assertEquals(1, productDao.getProductQuantity(tovar));
    }
}
