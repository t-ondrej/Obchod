/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.upjs.ics.obchod.entity;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.JdbcTemplateFactory;
import sk.upjs.ics.obchod.dao.mysql.MysqlProductDao;

/**
 *
 * @author Tomas
 */
public class CartTest {
    
    private Product p1, p2, p3, p4, p5;
    private Cart cart;
    private IProductDao productDao;
    
    public CartTest() {
    }
    
    @Before
    public void setUp() {
        cart = new Cart();
        productDao = new MysqlProductDao(JdbcTemplateFactory.INSTANCE.getTestTemplate());
        cart.setProductDao(productDao);
        fillTestingData();
    }
    
    @After
    public void tearDown() {
        cart = null;
    }
    
    private void fillTestingData() {
        Brand b1 = new Brand(1L, "B1"), b2 = new Brand(2L, "B2");
        Category c1 = new Category(1L, "C1"), c2 = new Category(2L, "C2");
        
        p1 = new Product(null, "P1", b1, c1, 10, 2);
        p2 = new Product(null, "P2", b2, c1, 20, 4);
        p3 = new Product(null, "P3", b2, c2, 30, 6);
        p4 = new Product(null, "P4", b1, c2, 40, 8);
        p5 = new Product(null, "P5", b1, c2, 50, 10);
        
        productDao.saveOrUpdate(p1);
        productDao.saveOrUpdate(p2);
        productDao.saveOrUpdate(p3);
        productDao.saveOrUpdate(p4);
        productDao.saveOrUpdate(p5);
        
        cart.addProductToCart(p1, 2);
        cart.addProductToCart(p2, 3);
        cart.addProductToCart(p3, 4);
        cart.addProductToCart(p4, 4);
    }

    @Test
    public void testGetCartProducts() {
        List<Product> products = cart.getCartProducts();
        products.forEach(p -> Assert.assertTrue(p.getId() != null));
        Assert.assertTrue(products.size() > 1);
    }

    @Test
    public void testAddProductToCart() {
        List<Product> productsOld = cart.getCartProducts();
        Assert.assertTrue(!productsOld.contains(p5));
        Assert.assertTrue(!cart.addProductToCart(p1, 1));
        cart.addProductToCart(p5, 1);
        Assert.assertTrue(cart.getCartProductQuantity(p5) == 1);
        Assert.assertTrue(cart.getCartProducts().contains(p5));
    }

    @Test
    public void testRemoveProductFromCart() {
        cart.removeProductFromCart(p1);
        Assert.assertTrue(cart.getCartProductQuantity(p1) == 1);
        cart.removeProductFromCart(p1);
        Assert.assertTrue(cart.getCartProductQuantity(p1) == 0);
    }

    @Test
    public void testGetCartProductQuantity() {
    }

    @Test
    public void testSetCartProductQuantity() {
    }

    @Test
    public void testClearCart() {
        int totalPrice = cart.getTotalPrice();
        cart.clearCart();
        Assert.assertTrue(cart.getCartProducts().isEmpty());
        Assert.assertTrue(cart.getTotalPrice() == 0);
        
        fillTestingData();
    }
    
}
