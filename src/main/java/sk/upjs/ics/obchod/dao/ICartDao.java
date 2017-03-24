package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;

public interface ICartDao {  
    
    void addProductToCart(Product product ,Cart cart);
    
    int getCartProductQuantity(Product product ,Cart cart);
    
    /**
     * Nastavi pre dvojicu tovar kosik pocet bez ohladu na to aky pocet tam bol predtym
     * @param Tovar
     * @param Kosik
     * @param pocet_kusov  pocet, ktory sa nastavi
     */
    void setProductQuantity(Product product ,Cart cart, int quantity);
    
    /**
     * Odoberie jeden tovar z kosika
     * @param idTovaru
     * @param idKosika
     */
    void removeProductFromCart(Product product ,Cart cart);
    
    List<Product> getCartProducts(Cart kosik);
    
    void clearCart(Cart cart);
            
}
