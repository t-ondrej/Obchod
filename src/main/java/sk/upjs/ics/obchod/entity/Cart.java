package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.ICartDao;
import sk.upjs.ics.obchod.dao.IProductDao;

public class Cart implements ICartDao {
    
    private ObservableMap<Product, IntegerProperty> cartProducts = FXCollections.observableHashMap();
    
    private final IntegerProperty totalPrice = new SimpleIntegerProperty(0);
    
    private IProductDao productDao = DaoFactory.INSTANCE.getMysqlProductDao();
    
    public ObservableMap<Product, IntegerProperty> getProducts() {
        return cartProducts;
    }
    
    public void setProductDao(IProductDao productDao) {
        this.productDao = productDao;
    }
    
    public int getTotalPrice() {
        return totalPrice.getValue();
    }
    
    public void setTotalPrice(int totalPrice) {
        this.totalPrice.set(totalPrice);
    }
    
    public IntegerProperty totalPriceProperty() {
        return totalPrice;
    }
      
    public IntegerProperty cartProductQuantityProperty(Product product) {
        return cartProducts.get(product);
    }

    public ObservableList<Product> cartProductsObservableList() {  
        return FXCollections.observableArrayList(getCartProducts());
    }
    
    @Override
    public List<Product> getCartProducts() {
        return new ArrayList<>(cartProducts.keySet());
    }

    @Override
    public boolean addProductToCart(Product product, int quantity) {
        int productQuantity = productDao.getProductQuantity(product);

        if (productQuantity < quantity) 
            return false;     
              
        if (!cartProducts.containsKey(product)) {
            cartProducts.put(product, new SimpleIntegerProperty(quantity));

        } else {
            int productCount = getCartProductQuantity(product);
            cartProducts.put(product, new SimpleIntegerProperty(productCount + quantity));
        }
        
        setTotalPrice(getTotalPrice() + product.getPrice() * quantity);
        
        int quantityBeforeAddition = productDao.getProductQuantity(product);
        productDao.setProductQuantity(product, quantityBeforeAddition - quantity);
        
        return true;
    }

    @Override
    public void removeProductFromCart(Product product) {

        if (cartProducts.get(product).getValue() <= 1) {
            cartProducts.remove(product);

        } else {
            int pocetTovaru = cartProducts.get(product).getValue();
            cartProducts.put(product, new SimpleIntegerProperty(pocetTovaru - 1));
        }

        totalPrice.setValue(getTotalPrice() - product.getPrice());
        
        int quantityBeforeRemoval = productDao.getProductQuantity(product);
        productDao.setProductQuantity(product, quantityBeforeRemoval + 1);
    }

    @Override
    public int getCartProductQuantity(Product product) {
        if (!cartProducts.containsKey(product))
            return 0;
        
        return cartProducts.get(product).getValue();
    }

    @Override
    public void setCartProductQuantity(Product product, int quantity) {
        int qty = quantity - getCartProductQuantity(product);
        int totalPrice = getTotalPrice();
        
        cartProducts.put(product, new SimpleIntegerProperty(quantity));

        setTotalPrice(totalPrice + (qty * product.getPrice()));
    }

    @Override
    public void clearCart() {
        List<Product> products = getCartProducts();
        
        int lol = getCartProductQuantity(products.get(0));
        int lol2 = productDao.getProductQuantity(products.get(0));
        
        int lol3 = getCartProductQuantity(products.get(1));
        int lol4 = productDao.getProductQuantity(products.get(1));
        
        products.forEach(p -> {
            int quantity = getCartProductQuantity(p);
            int quantityBeforeAddition = productDao.getProductQuantity(p);
            productDao.setProductQuantity(p, quantityBeforeAddition + quantity);
        });
              
        cartProducts = FXCollections.observableHashMap();
        setTotalPrice(0);
    }
}
