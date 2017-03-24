package sk.upjs.ics.obchod.managers;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Product;

public interface ICartManager {
    
    boolean addProductToCart(Product product);
    
    void removeProductFromCart(Product product);
    
    List<Product> getCartProducts();
    
    void clearCart();
    
    int getCartProductQuantity(Product product);
    
    IntegerProperty cartProductQuantityProperty(Product product);
    
    ObservableList<Product> cartProductsObservableList();
    
}
