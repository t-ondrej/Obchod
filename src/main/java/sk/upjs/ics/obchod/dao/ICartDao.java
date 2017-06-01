package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Product;

public interface ICartDao {    
    
    List<Product> getCartProducts();
    
    boolean addProductToCart(Product product, int quantity);
    
    void removeProductFromCart(Product product);
    
    int getCartProductQuantity(Product product);
    
    void setCartProductQuantity(Product product, int quantity);    
    
    void clearCart();
            
}
